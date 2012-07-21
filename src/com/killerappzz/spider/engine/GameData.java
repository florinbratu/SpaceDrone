package com.killerappzz.spider.engine;

import android.os.Parcel;
import android.os.Parcelable;

import com.killerappzz.spider.Constants;
import com.killerappzz.spider.R;

/**
 * Game-related info.
 * We use the parcelable shit to pass it 
 * between activities(ex game over activity)
 * 
 * @author florin
 *
 */
public class GameData implements Parcelable{
	// the score
	private long score;
	// the time
	private TimeHandler time;
	// the total number of lives
	private int lifes;
	// game over indicator
	private boolean gameOver;
	// if game over - success flag
	private boolean victory;
	/* Victory conditions:
	 * - escape through the hole
	 * Lose conditions:
	 * - no more life points
	 * - TODO time limit exceeded
	 *  */
	public enum EndGameCondition {
		NONE,
		// lose
		DEATH,
		TIMEOUT,
		// victory
		ESCAPE;
		
		public static final int descriptionStrings[] = new int[] {
			0,
			R.string.gameOver_death,
			R.string.gameOver_timeout,
			R.string.victory_message
		};
	}
	private EndGameCondition endReason;
	
	public GameData() {
		reset();
	}
	
	public void reset() {
		this.score = 0;
		this.time = new TimeHandler();
		this.lifes = Constants.MAX_LIFES;
		this.gameOver = false;
		this.victory = false;
		this.endReason = EndGameCondition.NONE;
	}
	
	public GameData(GameData orig) {
		this.score = orig.score;
		this.time = new TimeHandler(orig.time);
		this.lifes = orig.lifes;
		this.gameOver = orig.gameOver;
		this.victory = orig.victory;
		this.endReason = orig.endReason;
	}

	public long getScore() {
		return score;
	}
	
	public void addTime(float timeDeltaSeconds) {
		time.addTime(timeDeltaSeconds);
	}
	
	public int getMinutes() {return this.time.mins;}
	public int getSeconds() {return this.time.secs;}
	public int getDeciSeconds() {return this.time.deciSecs;}
	public float getTotalTime() {return this.time.totalTime;}
	
	private class TimeHandler{
		public int deciSecs;
		public int secs;
		public int mins;
		private float totalTime;

		public TimeHandler() {
			this.deciSecs = 0;
			this.secs = 0;
			this.mins = 0;
			this.totalTime = 0;
		}
		
		public TimeHandler(TimeHandler orig) {
			this.deciSecs = orig.deciSecs;
			this.secs = orig.secs;
			this.mins = orig.mins;
			this.totalTime = orig.totalTime;
		}

		public void addTime(float timeDeltaSecs) {
			this.totalTime += timeDeltaSecs;
			this.mins = (int)(this.totalTime / 60);
			this.secs = (int)this.totalTime - this.mins * 60;
			float decimal = this.totalTime - (int)this.totalTime;
			this.deciSecs = (int)(10.0f * decimal);
		}

		public void update(TimeHandler orig) {
			this.deciSecs = orig.deciSecs;
			this.secs = orig.secs;
			this.mins = orig.mins;
			this.totalTime = orig.totalTime;
		}
	}
	
	public int getLifesCount() {
		return lifes;
	}
	
	public void lostLife() {
		this.lifes--;
		if(this.lifes == 0) 
			death(EndGameCondition.DEATH);
	}
	
	public boolean gameOver() {
		return this.gameOver;
	}
	
	public boolean victorious() {
		return this.victory;
	}

	public void death(EndGameCondition endCond) {
		this.gameOver = true;
		this.victory = false;
		this.endReason = endCond;
	}
	
	public void victory(EndGameCondition endCond) {
		this.gameOver = true;
		this.victory = true;
		this.endReason = endCond;
	}
	
	public EndGameCondition getEndGameReason() {
		return endReason;
	}

	// update at each frame
	public void update(GameData omolog) {
		this.score = omolog.score;
		this.time.update(omolog.time);
		this.lifes = omolog.lifes;
		this.gameOver = omolog.gameOver;
		this.victory = omolog.victory;
		this.endReason = omolog.endReason;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(score);
		dest.writeFloat(time.totalTime);
		dest.writeInt(lifes);
		dest.writeBooleanArray(new boolean[]{gameOver,victory});
		dest.writeInt(this.endReason.ordinal());
	}
	
	public static final Parcelable.Creator<GameData> CREATOR
		= new Parcelable.Creator<GameData>() {
		public GameData createFromParcel(Parcel in) {
			return new GameData(in);
		}

		public GameData[] newArray(int size) {
			return new GameData[size];
		}
	};
	
	private GameData(Parcel in) {
		this.score = in.readLong();
		this.time = new TimeHandler();
		this.time.totalTime = in.readFloat();
		this.lifes = in.readInt();
		boolean array[] = new boolean[2];
		in.readBooleanArray(array);
		this.gameOver = array[0];
		this.victory = array[1];
		this.endReason = EndGameCondition.values()[in.readInt()];
	}

}
