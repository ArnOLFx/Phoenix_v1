package ets2;

import android.swedspot.automotiveapi.AutomotiveSignalId;
import android.swedspot.scs.data.SCSFloat;
import android.swedspot.scs.data.SCSShort;

import combitech.sdp.simulator.BasicModule;
import combitech.sdp.simulator.SimulationModuleState;
import combitech.sdp.simulator.SimulatorGateway;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.ServerSocket;
import java.net.Socket;

public class EuroTruck extends BasicModule {
	
	//Declare server variables
	ServerSocket welcomeSocket;
	private Socket clientSocket;
	
	//Declare signals variables
	short currentGear;
	float fuelLevel;
	float fuelConsumption;
	float speed;
	float brake;
	float engineSpeed;
	
	//Simulator Gateway Constructor
	public EuroTruck(SimulatorGateway gateway) {
		super(gateway);
	}
	
	//Define provided signals
	@Override
	public int[] getProvidingSignals() {
		return new int[] { AutomotiveSignalId.FMS_WHEEL_BASED_SPEED,
				AutomotiveSignalId.FMS_CURRENT_GEAR,
				AutomotiveSignalId.FMS_ENGINE_SPEED,
				AutomotiveSignalId.FMS_TACHOGRAPH_VEHICLE_SPEED,
				AutomotiveSignalId.FMS_FUEL_RATE,
//				AutomotiveSignalId.FMS_FUEL_LEVEL_1,
//				AutomotiveSignalId.FMS_HIGH_RESOLUTION_TOTAL_VEHICLE_DISTANCE,
//				AutomotiveSignalId.FMS_ACCELERATOR_PEDAL_POSITION_1 
				};
	}

	//run implementation
	@Override
	public void run() {
		//Set Module Thread name
		getModuleThread().setName("ETS2");
		
		//Declare variable that will store the latest acquired values from ETS2
		String signalUpdate;
		
		//While simulation module is running, initialise servers
		while (state == SimulationModuleState.RUNNING) {
			try {
				if (welcomeSocket == null) {
					//Initialise server socket
					welcomeSocket = new ServerSocket(6000);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				//Initialise client socket (address where ETS2 data is being sent by ETS2 Telemetry Server
				clientSocket = new Socket("127.0.0.1", 25555);
				
				//Initialise PrintWriter, used to send refresh command to the ETS2 Telemetry server
				PrintWriter out = new PrintWriter(
						clientSocket.getOutputStream(), true);
				//REST API command to refresh telemetry data
				out.println("GET /api/ets2/telemetry");
				out.flush();

				//Initialise Buffered Reader that gets input stream data from ETS2 Telemetry server
				BufferedReader inFromETS2 = new BufferedReader(
						new InputStreamReader(clientSocket.getInputStream()));
				
				//While simulation module is running and client socket is connected
				while (state == SimulationModuleState.RUNNING
						&& clientSocket.isConnected()) {
					
					//initialise variable signalUpdate with latest values from ETS2 
					signalUpdate = inFromETS2.readLine();
					if (signalUpdate == null) {
						break;
					}
					if (signalUpdate.length() > 30) {
						//map ETS2 values into AGA/FMS signals
						extractValues(signalUpdate);
						gateway.sendValue(
								AutomotiveSignalId.FMS_WHEEL_BASED_SPEED,
								new SCSFloat(speed));
						gateway.sendValue(AutomotiveSignalId.FMS_CURRENT_GEAR,
								new SCSShort(currentGear));
						gateway.sendValue(AutomotiveSignalId.FMS_ENGINE_SPEED,
								new SCSFloat(engineSpeed));
						gateway.sendValue(AutomotiveSignalId.FMS_TACHOGRAPH_VEHICLE_SPEED,
								new SCSFloat(brake));
						gateway.sendValue(AutomotiveSignalId.FMS_FUEL_RATE,
								new SCSFloat(fuelConsumption));
					}
					Thread.sleep(18);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	/*Method that stores telemetry data values into a string array, 
	and initialise signals variables with corresponding values*/
	private void extractValues(String signalUpdate) {
		String[] values = signalUpdate.split(",");

		try {
			speed = Float.parseFloat(values[6].substring(13));
			currentGear = Short.parseShort(values[16].substring(7));
			engineSpeed = Float.parseFloat(values[20].substring(12));
			brake = Float.parseFloat(values[31].substring(12));
			fuelConsumption = Float.parseFloat(values[24].substring(25));
			fuelConsumption *= 100;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Stop simulation module method
	@Override
	public void stopModule() {
		state = SimulationModuleState.STOPPED;
		try {
			if (clientSocket != null) {
				clientSocket.close();
			}
			if (welcomeSocket != null) {
				welcomeSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.stopModule();
	}
}
