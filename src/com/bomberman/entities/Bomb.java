package com.bomberman.entities;

import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import com.bomberman.dto.BombDto;
import com.bomberman.graphics.Sprite;
import com.bomberman.server.GameActionPerformed;

public class Bomb extends Entity implements Destructible {

	public static final int BOMB_RANGE = 2;
	public static final int TIME_TO_EXPLOIT = 3000;

	private Set<ExplosionDirection> explosionDirections;
	private Timer timerInstace;
	private int id;
	private GameActionPerformed gameActionPerformedListener;
	// private ExplosionListener listener;
	private Timer spriteTimer;

	public Bomb(int x, int y, GameActionPerformed gameActionPerformedListener /* , ExplosionListener listener */,
			int id) {
		super(x, y);
		// this.listener = listener;
		this.id = id;
		this.gameActionPerformedListener = gameActionPerformedListener;
		this.explosionDirections = new HashSet<>();
		this.sprite = Sprite.bomb;
		this.animate();
		this.timerInstace = new Timer();
		this.timerInstace.schedule(new TimerTask() {
			@Override
			public void run() {
				destroy();
			}
		}, TIME_TO_EXPLOIT);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Bomb other = (Bomb) obj;
		if (this.x != other.x || this.y != other.y) {
			return false;
		}
		return true;
	}

	@Override
	public void destroy() {
		if(this.destroyed) return;
		// this.setDestroyed(true);
		this.setPainted(true);
		this.gameActionPerformedListener.explodeBomb(this);
	}

	public BombDto toDto() {
		return new BombDto((int) x, (int) y, id, animateCount, destroyed, painted);
	}

	public void addExplotionDirection(Entity range1, Entity range2, ExplosionDirection max, ExplosionDirection min) {
		if (range1 == null) {
			if (range2 == null) {
				this.explosionDirections.add(max);
			} else {
				this.explosionDirections.add(min);
			}
		}
	}

	public Set<ExplosionDirection> getExplosionDirections() {
		return explosionDirections;
	}

	public void cancelTimer() {
		this.timerInstace.cancel();
		// this.spriteTimer.cancel();
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public void animate() {
		this.spriteTimer = new Timer();
		this.spriteTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				incrementAnimateCount();
			}
		}, 0, 200);
	}

}
