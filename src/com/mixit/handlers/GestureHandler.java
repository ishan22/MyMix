package com.mixit.handlers;

import java.io.IOException;

import com.mixit.converters.Converter;
import com.thalmic.myo.DeviceListener;
import com.thalmic.myo.FirmwareVersion;
import com.thalmic.myo.Myo;
import com.thalmic.myo.Pose;
import com.thalmic.myo.Quaternion;
import com.thalmic.myo.Vector3;
import com.thalmic.myo.enums.Arm;
import com.thalmic.myo.enums.PoseType;
import com.thalmic.myo.enums.UnlockType;
import com.thalmic.myo.enums.VibrationType;
import com.thalmic.myo.enums.WarmupResult;
import com.thalmic.myo.enums.WarmupState;
import com.thalmic.myo.enums.XDirection;


public class GestureHandler implements DeviceListener{

	PoseType currentPose;
	SoundHandler soundHandle;
	VolumeHandler volumeControl;
	double lastPitch;
	double lastSpeed;

	public GestureHandler(SoundHandler soundHandle, VolumeHandler volumeControl){
		this.soundHandle = soundHandle;
		this.volumeControl = volumeControl;
	}

	@Override
	public void onPair(Myo myo, long timestamp, FirmwareVersion firmwareVersion) {
		System.out.println("Myo paired!");

	}

	@Override
	public void onUnpair(Myo myo, long timestamp) {
		System.out.println("Myo unpaired!");
	}

	@Override
	public void onConnect(Myo myo, long timestamp, FirmwareVersion firmwareVersion) {
		System.out.println("Myo connected!");

	}

	@Override
	public void onDisconnect(Myo myo, long timestamp) {

		System.out.println("Myo disconnected!");

	}

	@Override
	public void onArmSync(Myo myo, long timestamp, Arm arm, XDirection xDirection, WarmupState warmupState) {
		System.out.println("Arm synced!");

	}

	@Override
	public void onArmUnsync(Myo myo, long timestamp) {
		System.out.println("Arm unsynced!");

	}

	@Override
	public void onUnlock(Myo myo, long timestamp) {
		System.out.println("Myo unlocked!");
		myo.vibrate(VibrationType.VIBRATION_LONG);
		myo.unlock(UnlockType.UNLOCK_HOLD);
	}

	@Override
	public void onLock(Myo myo, long timestamp) {
	}

	@Override
	public void onPose(Myo myo, long timestamp, Pose pose) {
		if(pose.getType() == PoseType.FIST){
			currentPose = PoseType.FIST;
		}
		else if(pose.getType() == PoseType.REST){
			currentPose = PoseType.REST;
		}
		else if(pose.getType() == PoseType.FINGERS_SPREAD){
			currentPose = PoseType.FINGERS_SPREAD;
		}
		else if(pose.getType() == PoseType.DOUBLE_TAP){
			currentPose = PoseType.DOUBLE_TAP;
			soundHandle.stop();
		}
		System.out.println("Pose struck: " + pose);

	}

	@Override
	public void onOrientationData(Myo myo, long timestamp, Quaternion rotation) {
		System.out.println("Orientation Changed: " + rotation);

	}

	@Override
	public void onAccelerometerData(Myo myo, long timestamp, Vector3 accel) {
		if(currentPose == PoseType.FINGERS_SPREAD){
			lastSpeed = accel.getX();
			soundHandle.changeSpeed(lastSpeed);
			System.out.println("Speed: " + lastSpeed);
//			volumeControl.adjustVolume(lastVolume);
		}
		else if(currentPose == PoseType.REST){
			
		}

	}

	@Override
	public void onGyroscopeData(Myo myo, long timestamp, Vector3 gyro) {
		double newPitch = (gyro.getX() + gyro.getY() + gyro.getZ()) * 0.5;
		if(currentPose == PoseType.FIST){
			soundHandle.changePitch(newPitch);
			lastPitch = newPitch;
		}
		else {
			soundHandle.changePitch(0);
		}
		System.out.println("Gyro: " + gyro);
	}

	@Override
	public void onRssi(Myo myo, long timestamp, int rssi) {}

	@Override
	public void onBatteryLevelReceived(Myo myo, long timestamp, int level) {}

	@Override
	public void onEmgData(Myo myo, long timestamp, byte[] emg) {}

	@Override
	public void onWarmupCompleted(Myo myo, long timestamp, WarmupResult warmupResult) {}
}

