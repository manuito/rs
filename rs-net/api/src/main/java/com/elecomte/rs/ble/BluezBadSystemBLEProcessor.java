package com.elecomte.rs.ble;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Emmanuel Lecomte (elecomte)
 *
 */
@Component
public class BluezBadSystemBLEProcessor extends NonContextualBLEProcessor {

	protected static final Logger LOGGER = LoggerFactory.getLogger(BluezBadSystemBLEProcessor.class);

	@Value("${rs.ble.bluez.bad-processor-log}")
	private boolean outputAll = false;

	private static final String SCAN_TOOL = "hcitool";

	private static final String SCAN_CMD = "lescan";

	private static final String DEVICE_TOOL = "gatttool";
	private static final String DEVICE_MODE = "-t random";
	private static final String DEVICE_ADDR_ARG = "-b ";

	private static final String CHAR_LIST_CMD = "--characteristics";

	private static final String CHAR_READ_CMD = "--char-read";
	private static final String CHAR_HANDLE_ARG = "--handle=";

	private static final String CHAR_WRITE_CMD = "--char-write-req";
	private static final String CHAR_VALUE_ARG = "--value=";
	private static final String CHAR_LISTEN_ARG = "--listen";
	private static final String CHAR_LISTEN_VALUE = "0x0001";

	private static final String CHAR_WRITE_SUCCESS = "Characteristic value was written successfully";

	@Override
	public boolean isReady() throws BLEStackException {
		List<String> procs = processedCommand("ps", "-e");

		for (String proc : procs) {

			if (proc.trim().endsWith(SCAN_TOOL)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @return
	 * @throws BLEStackException
	 * @see com.elecomte.rs.ble.BLEProcessor#bleScan()
	 */
	@Override
	public List<DeviceIdentifier> bleScan() throws BLEStackException {

		launchCommandTimeoutSurvey(SCAN_TOOL, 2000);
		List<String> res = sub(processedCommand(SCAN_TOOL, SCAN_CMD), 1, 0);
		return conv(res, BluezBadSystemBLEProcessor::deviceIdentifierFromScan);
	}

	/**
	 * @param id
	 * @return
	 * @throws BLEStackException
	 * @see com.elecomte.rs.ble.BLEProcessor#getDeviceCharacteristics(com.elecomte.rs.ble.DeviceIdentifier)
	 */
	@Override
	public List<CharacteristicIdentifier> getDeviceCharacteristics(DeviceIdentifier id) throws BLEStackException {

		List<String> res = processedCommand(DEVICE_TOOL, DEVICE_MODE, DEVICE_ADDR_ARG + id.getAddress(), CHAR_LIST_CMD);
		return conv(res, BluezBadSystemBLEProcessor::charIdentifierFromList);
	}

	/**
	 * @param id
	 * @param charId
	 * @return
	 * @throws BLEStackException
	 * @see com.elecomte.rs.ble.BLEProcessor#readCharacteristic(com.elecomte.rs.ble.DeviceIdentifier,
	 *      com.elecomte.rs.ble.CharacteristicIdentifier)
	 */
	@Override
	public String readCharacteristic(DeviceIdentifier id, CharacteristicIdentifier charId) throws BLEStackException {

		List<String> res = processedCommand(DEVICE_TOOL, DEVICE_MODE, DEVICE_ADDR_ARG + id.getAddress(), CHAR_READ_CMD,
				CHAR_HANDLE_ARG + charId.getHandle());
		if (res.size() == 0) {
			throw new BLEStackException("Unknown Characteristic " + charId);
		}
		return res.get(0);
	}

	/**
	 * @param id
	 * @param charId
	 * @param newValue
	 * @return
	 * @throws BLEStackException
	 */
	@Override
	public boolean writeCharacteristic(DeviceIdentifier id, CharacteristicIdentifier charId, String newValue)
			throws BLEStackException {
		List<String> res = processedCommand(DEVICE_TOOL, DEVICE_MODE, DEVICE_ADDR_ARG + id.getAddress(),
				CHAR_WRITE_CMD, CHAR_HANDLE_ARG + charId.getHandle(), CHAR_VALUE_ARG + newValue);

		if (res.size() == 0) {
			throw new BLEStackException("Unknown Characteristic " + charId);
		}

		return res.get(0).equals(CHAR_WRITE_SUCCESS);
	}

	/**
	 * @param string
	 * @return
	 */
	private static <T> List<T> conv(List<String> res, Function<String, ? extends T> mapper) {
		return res.stream().map(mapper).collect(Collectors.toList());
	}

	/**
	 * @param id
	 * @param charId
	 * @param listener
	 * @return
	 * @throws BLEStackException
	 * @see com.elecomte.rs.ble.BLEProcessor#launchCharacteristicListener(com.elecomte.rs.ble.DeviceIdentifier,
	 *      com.elecomte.rs.ble.CharacteristicIdentifier, com.elecomte.rs.ble.CharacteristicListener)
	 */
	@Override
	public boolean launchCharacteristicListener(DeviceIdentifier id, CharacteristicIdentifier charId,
			final CharacteristicListener listener) throws BLEStackException {

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {
				processedCommand(DEVICE_TOOL, DEVICE_MODE, DEVICE_ADDR_ARG + id.getAddress(), CHAR_WRITE_CMD,
						CHAR_HANDLE_ARG + charId.getHandle(), CHAR_VALUE_ARG + CHAR_LISTEN_VALUE, CHAR_LISTEN_ARG);
			}
		});

		th.start();

		return false;
	}

	/**
	 * @return
	 * @see com.elecomte.rs.ble.BLEProcessor#stopAll()
	 */
	@Override
	public boolean stopAll() {

		List<String> procs = processedCommand("ps", "-e");

		for (String proc : procs) {

			if (proc.trim().endsWith(DEVICE_TOOL) || proc.trim().endsWith(SCAN_TOOL)) {
				String[] parts = proc.split(" ");
				String pid = parts[0];

				// Case start with a space
				if (pid == null || pid.length() == 0) {
					pid = parts[1];
				}

				processedCommand("kill", "-2", pid);
			}
		}

		return true;
	}

	/**
	 * 
	 */
	@PostConstruct
	protected void postInit() {
		LOGGER.info("Using the BAD bluez BLE processor. {}",
				(this.outputAll ? "All commands will be sent to console for debug" : ""));
	}

	/**
	 * @param command
	 * @param timeout
	 */
	protected void launchCommandTimeoutSurvey(final String command, final long timeout) {

		// Scan can be stopped properly with CTRL-C only (SIGINT = 2)
		// So after fixed timeout, stop it properly ... search for its PID and kill -2 it
		Thread chk = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					// Process search for kill after timeout only
					Thread.sleep(timeout);

					List<String> procs = processedCommand("ps", "-e");

					for (String proc : procs) {

						if (proc.trim().endsWith(command)) {
							String[] parts = proc.split(" ");
							String pid = parts[0];

							// Case start with a space
							if (pid == null || pid.length() == 0) {
								pid = parts[1];
							}

							processedCommand("kill", "-2", pid);
						}
					}

				} catch (BLEStackException | InterruptedException e) {
					LOGGER.error("cannot handle timeout process", e);
				}
			}

		});

		chk.start();
	}

	/**
	 * @param command
	 * @param args
	 * @return
	 * @throws BLEProcessException
	 */
	protected List<String> processedCommand(String command, String... args) throws BLEStackException {

		// Build command
		List<String> commands = new ArrayList<>();
		commands.add(command);

		// Add arguments
		for (String arg : args) {
			commands.add(arg);
		}

		try {
			// Run macro (use default target
			ProcessBuilder pb = new ProcessBuilder(commands);

			if (this.outputAll) {
				System.out.println("[CMD] " + pb.command());
			}

			pb.redirectErrorStream(true);
			Process process = pb.start();

			List<String> results = new ArrayList<>();

			// Read output
			StringBuilder out = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null, previous = null;

			while ((line = br.readLine()) != null) {

				if (this.outputAll) {
					System.out.println("[OUT] " + line);
				}

				if (!line.equals(previous)) {
					previous = line;
					results.add(line);
				}
			}

			// Check result
			if (process.waitFor() >= 0) {
				return results;
			}

			throw new BLEStackException("Process cannot terminate [" + out.toString() + "]");
		}

		catch (IOException | InterruptedException e) {
			throw new BLEStackException("Process cannot be launched", e);
		}
	}

	/**
	 * Need to exclud some useless lines
	 * 
	 * @param res
	 * @param ignoreAtStart
	 * @param ignoreAtEnd
	 * @return
	 */
	private static List<String> sub(List<String> res, int ignoreAtStart, int ignoreAtEnd) {
		if (ignoreAtStart == 0 && ignoreAtEnd == 0)
			return res;

		return res.subList(ignoreAtStart, res.size() - ignoreAtEnd);
	}

	/**
	 * @param line
	 * @return
	 */
	private static DeviceIdentifier deviceIdentifierFromScan(String line) {

		// Address model : 17 chars
		String address = line.substring(0, 17);
		String name = line.substring(18);
		return new DeviceIdentifier(name, address);
	}

	/**
	 * @param line
	 * @return
	 */
	private static CharacteristicIdentifier charIdentifierFromList(String line) {
		// Model : handle = 0x0002, char properties = 0x02, char value handle = 0x0003, uuid =
		// 00002a00-0000-1000-8000-00805f9b34fb
		String handle = line.substring(9, 15);
		String properties = line.substring(35, 39);
		String valueHandle = line.substring(61, 67);
		String uuid = line.substring(76);
		return new CharacteristicIdentifier(handle, properties, valueHandle, uuid);
	}
}
