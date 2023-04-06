package com.game.characters;

import java.net.SecureCacheResponse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.project.project;

public class Player extends Sprite{
	// HP, movement speed value
	private float hp, speed;
	
	private float atkAniTime;
		
	// Character sprite sheet
	private TextureRegion[] walkUp, walkDown, walkLeft, walkRight,
						idleRight, idleLeft, idleUp, idleDown, 
						attackUp1, attackUp2, attackUp3, 
						attackDown1, attackDown2, attackDown3, 
						attackLeft1, attackLeft2, attackLeft3, 
						attackRight1, attackRight2, attackRight3;
	
	// Character animation
	private Animation<TextureRegion> walkUpAni, walkDownAni, walkLeftAni, walkRightAni,
								idleUpAni, idleDownAni, idleLeftAni, idleRightAni,
								attackUpAni1, attackUpAni2, attackUpAni3, 
								attackDownAni1, attackDownAni2, attackDownAni3, 
								attackLeftAni1, attackLeftAni2, attackLeftAni3, 
								attackRightAni1, attackRightAni2, attackRightAni3;
		
	//
	private float elapsedTime;
		
	//
	private TextureRegion[][] tmpFrame;
		
	// Character States
	public enum State {
		IDLE_UP, IDLE_RIGHT, IDLE_LEFT, IDLE_DOWN, 
		WALK_UP, WALK_LEFT, WALK_RIGHT, WALK_DOWN, 
		WALK_LEFT_DOWN, WALK_RIGHT_DOWN,
		WALK_LEFT_UP, WALK_RIGHT_UP,
		ATTACK_UP, ATTACK_DOWN, ATTACK_LEFT, ATTACK_RIGHT
	}
		
	// Current character state
	private State currentState;
	
	// Boolean
	private boolean isMoving, isAttacking;

	public Player(float hp, float speed, Texture walkUpTexture, Texture walkDownTexture, 
			Texture walkLeftTexture, Texture walkRightTexture, Texture attackUpTexture,
			Texture attackDownTexture, Texture attackRightTexture, Texture attackLeftTexture) {
		this.hp = hp;
		this.speed = speed;
		
		tmpFrame = TextureRegion.split(walkUpTexture, 64, 64);
		
		idleUp = new TextureRegion[6];
		walkUp = new TextureRegion[6];
		
		int index = 0;
		
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 6; j++) {
				idleUp[index] = tmpFrame[0][j];
				walkUp[index] = tmpFrame[1][j];
				index++;
			}
		}
		
		idleUpAni = new Animation<TextureRegion>(1f / 6f, idleUp);
		walkUpAni = new Animation<TextureRegion>(1f / 6f, walkUp);
		
		index = 0;
		
		tmpFrame = TextureRegion.split(walkDownTexture, 64, 64);
		
		idleDown = new TextureRegion[6];
		walkDown = new TextureRegion[6];
		
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 6; j++) {
				idleDown[index] = tmpFrame[0][j];
				walkDown[index] = tmpFrame[1][j];
				index++;
			}
		}
		
		idleDownAni = new Animation<TextureRegion>(1f / 6f, idleDown);
		walkDownAni = new Animation<TextureRegion>(1f / 6f, walkDown);
		
		index = 0;
		
		tmpFrame = TextureRegion.split(walkLeftTexture, 64, 64);
		
		idleLeft = new TextureRegion[6];
		walkLeft = new TextureRegion[6];
		
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 6; j++) {
				idleLeft[index] = tmpFrame[0][j];
				walkLeft[index] = tmpFrame[1][j];
				index++;
			}
		}
		
		idleLeftAni = new Animation<TextureRegion>(1f / 6f, idleLeft);
		walkLeftAni = new Animation<TextureRegion>(1f / 6f, walkLeft);
		
		index = 0;
		
		tmpFrame = TextureRegion.split(walkRightTexture, 64, 64);
		
		idleRight = new TextureRegion[6];
		walkRight = new TextureRegion[6];
		
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 6; j++) {
				idleRight[index] = tmpFrame[0][j];
				walkRight[index] = tmpFrame[1][j];
				index++;
			}
		}
		
		idleRightAni = new Animation<TextureRegion>(1f / 6f, idleRight);
		walkRightAni = new Animation<TextureRegion>(1f / 6f, walkRight);
		
		index = 0;
		
		tmpFrame = TextureRegion.split(attackUpTexture, 64, 64);
		
		attackUp1 = new TextureRegion[6];
		attackUp2 = new TextureRegion[9];
		attackUp3 = new TextureRegion[7];
		
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 6; j++) {
				attackUp1[index++] = tmpFrame[0][j];
			}
		}
		
		attackUpAni1 = new Animation<TextureRegion>(1f / 12f, attackUp1);
		
		index = 0;
		
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 9; j++) {
				attackUp2[index++] = tmpFrame[1][j];
			}
		}
		
		attackUpAni2 = new Animation<TextureRegion>(1f / 13.5f, attackUp2);
		
		index = 0;
		
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 7; j++) {
				attackUp3[index++] = tmpFrame[2][j];
			}
		}
		
		attackUpAni3 = new Animation<TextureRegion>(1f / 14f, attackUp3);
		
		index = 0;
		
		tmpFrame = TextureRegion.split(attackDownTexture, 64, 64);
		
		attackDown1 = new TextureRegion[6];
		attackDown2 = new TextureRegion[9];
		attackDown3 = new TextureRegion[7];
		
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 6; j++) {
				attackDown1[index++] = tmpFrame[0][j];
			}
		}
		
		attackDownAni1 = new Animation<TextureRegion>(1f / 12f, attackDown1);
		
		index = 0;
		
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 9; j++) {
				attackDown2[index++] = tmpFrame[1][j];
			}
		}
		
		attackDownAni2 = new Animation<TextureRegion>(1f / 15f, attackDown2);
		
		index = 0;
		
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 7; j++) {
				attackDown3[index++] = tmpFrame[2][j];
			}
		}
		
		attackDownAni3 = new Animation<TextureRegion>(1f / 14f, attackDown3);
		
		index = 0;
		
		tmpFrame = TextureRegion.split(attackLeftTexture, 64, 64);
		
		attackLeft1 = new TextureRegion[6];
		attackLeft2 = new TextureRegion[9];
		attackLeft3 = new TextureRegion[7];
		
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 6; j++) {
				attackLeft1[index++] = tmpFrame[0][j];
			}
		}
		
		attackLeftAni1 = new Animation<TextureRegion>(1f / 12f, attackLeft1);
		
		index = 0;
		
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 9; j++) {
				attackLeft2[index++] = tmpFrame[1][j];
			}
		}
		
		attackLeftAni2 = new Animation<TextureRegion>(1f / 15f, attackLeft2);
		
		index = 0;
		
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 7; j++) {
				attackLeft3[index++] = tmpFrame[2][j];
			}
		}
		
		attackLeftAni3 = new Animation<TextureRegion>(1f / 14f, attackLeft3);
		
		index = 0;
		
		tmpFrame = TextureRegion.split(attackRightTexture, 64, 64);
		
		attackRight1 = new TextureRegion[6];
		attackRight2 = new TextureRegion[9];
		attackRight3 = new TextureRegion[7];
		
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 6; j++) {
				attackRight1[index++] = tmpFrame[0][j];
			}
		}
		
		attackRightAni1 = new Animation<TextureRegion>(1f / 12f, attackRight1);
		
		index = 0;
		
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 9; j++) {
				attackRight2[index++] = tmpFrame[1][j];
			}
		}
		
		attackRightAni2 = new Animation<TextureRegion>(1f / 15f, attackRight2);
		
		index = 0;
		
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 7; j++) {
				attackRight3[index++] = tmpFrame[2][j];
			}
		}
		
		attackRightAni3 = new Animation<TextureRegion>(1f / 14f, attackRight3);
		
		index = 0;
	}

	public void update(float delta) {
		elapsedTime += delta;
	}
	
	public void draw() {
		isMoving = false;
		isAttacking = false;
		atkAniTime = 0.5f;
		
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
		    if (Gdx.input.isKeyPressed(Keys.UP)) {
		        currentState = State.WALK_RIGHT_UP;
		    } else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
		        currentState = State.WALK_RIGHT_DOWN;
		    } else {
		        currentState = State.WALK_RIGHT;
		    }	    
		    isMoving = true;
		} else if (Gdx.input.isKeyPressed(Keys.LEFT)) {
		    if (Gdx.input.isKeyPressed(Keys.UP)) {
		        currentState = State.WALK_LEFT_UP;
		    } else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
		        currentState = State.WALK_LEFT_DOWN;
		    } else {
		        currentState = State.WALK_LEFT;
		    }
		    isMoving = true;
		} else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			currentState = State.WALK_DOWN;
		    isMoving = true;
		} else if (Gdx.input.isKeyPressed(Keys.UP)) {
		    currentState = State.WALK_UP;
		    isMoving = true;
		} else if (Gdx.input.isKeyPressed(Keys.A)) {
			isAttacking = true;
			if (currentState == State.WALK_UP || currentState == State.IDLE_UP) {
				currentState = State.ATTACK_UP;
			} else if(currentState == State.WALK_DOWN || currentState == State.IDLE_DOWN) {
				currentState = State.ATTACK_DOWN;
					
			} else if(currentState == State.WALK_RIGHT || currentState == State.WALK_RIGHT_UP || 
					currentState == State.WALK_RIGHT_DOWN || currentState == State.IDLE_RIGHT) {
				currentState = State.ATTACK_LEFT;
			} else if (currentState == State.WALK_LEFT || currentState == State.WALK_LEFT_UP || 
					currentState == State.WALK_LEFT_DOWN || currentState == State.IDLE_LEFT) {
				currentState = State.ATTACK_RIGHT;
			} 
		} else {
		    isMoving = false;
		    isAttacking = false;
		    if (currentState == State.WALK_UP || currentState == State.ATTACK_UP) {		    	
		    	currentState = State.IDLE_UP;
		    }
		    else if (currentState == State.WALK_LEFT || currentState == State.ATTACK_LEFT) {		    	
		    	currentState = State.IDLE_LEFT;
		    }
		    else if (currentState == State.WALK_LEFT_UP) {
		    	currentState = State.IDLE_LEFT;
		    }
		    else if (currentState == State.WALK_LEFT_DOWN) {		    	
		    	currentState = State.IDLE_LEFT;
		    }
		    else if (currentState == State.WALK_RIGHT || currentState == State.ATTACK_RIGHT) {		    	
		    	currentState = State.IDLE_RIGHT;
		    }
		    else if (currentState == State.WALK_RIGHT_UP) {
		    	currentState = State.IDLE_RIGHT;	
		    }
		    else if (currentState == State.WALK_RIGHT_DOWN) {
		    	currentState = State.IDLE_RIGHT;
		    }
		    else if (currentState == State.WALK_DOWN || currentState == State.ATTACK_DOWN) {
		    	currentState = State.IDLE_DOWN;
		    }	       
		} 
		
		
		
		
		if (isMoving && !isAttacking) {
	        if (currentState == State.WALK_UP) {
	            walkUp();
	        }
	        else if (currentState == State.WALK_LEFT) {
	            walkLeft();
	        }
	        else if (currentState == State.WALK_LEFT_UP) {
	        	walkUpLeft();
	        }
	        else if (currentState == State.WALK_LEFT_DOWN) {
	        	walkDownLeft();
	        }
	        else if (currentState == State.WALK_RIGHT) {
	            walkRight();
	        }
	        else if (currentState == State.WALK_RIGHT_UP) {
	        	walkUpRight();
	        }
	        else if (currentState == State.WALK_RIGHT_DOWN) {
	        	walkDownRight();
	        }
	        else if (currentState == State.WALK_DOWN) {
	            walkDown();
	        }
		} else if (!isMoving && !isAttacking) {
			if (currentState == State.IDLE_UP)
		        idleUp();
		    else if (currentState == State.IDLE_LEFT)
		        idleLeft();
		    else if (currentState == State.IDLE_RIGHT)
		        idleRigt();
		    else if (currentState == State.IDLE_DOWN)
		        idleDown();
		} else if (isAttacking) {
			if (currentState == State.ATTACK_UP) {
				attackUp2();
				setBounds(getX(), getY(), 100, 100);
			} else if (currentState == State.ATTACK_LEFT) {
				attackLeft2();
				setBounds(getX(), getY(), 100, 100);
			} else if (currentState == State.ATTACK_RIGHT) {
				attackRight2();
				setBounds(getX(), getY(), 100, 100);
			} else if (currentState == State.ATTACK_DOWN) {
				attackDown2();
				setBounds(getX(), getY(), 100, 100);
			}
		}
	}

	
	public void idleUp() {
		project.batch.draw(idleUpAni.getKeyFrame(elapsedTime, true), getX(), getY());
	}
	
	public void walkUp() {
		project.batch.draw(walkUpAni.getKeyFrame(elapsedTime, true), getX(), getY());
		setPosition(getX(), getY() + speed * Gdx.graphics.getDeltaTime());
	}
	
	public void idleDown() {
		project.batch.draw(idleDownAni.getKeyFrame(elapsedTime, true), getX(), getY());
	}
	
	public void walkDown() {
		project.batch.draw(walkDownAni.getKeyFrame(elapsedTime, true), getX(), getY());
		setPosition(getX(), getY() - speed * Gdx.graphics.getDeltaTime());
	}
	
	public void idleLeft() {
		project.batch.draw(idleLeftAni.getKeyFrame(elapsedTime, true), getX(), getY());
	}
	
	public void walkLeft() {
		project.batch.draw(walkLeftAni.getKeyFrame(elapsedTime, true), getX(), getY());
		setPosition(getX() - speed * Gdx.graphics.getDeltaTime(), getY());
	}
	
	public void idleRigt() {
		project.batch.draw(idleRightAni.getKeyFrame(elapsedTime, true), getX(), getY());
	}
	
	public void walkRight() {
		project.batch.draw(walkRightAni.getKeyFrame(elapsedTime, true), getX(), getY());
		setPosition(getX() + speed * Gdx.graphics.getDeltaTime(), getY());
	}
	
	public void walkUpRight() {
		project.batch.draw(walkRightAni.getKeyFrame(elapsedTime, true), getX(), getY());
		setPosition(getX() + speed * Gdx.graphics.getDeltaTime(), getY() + speed * Gdx.graphics.getDeltaTime());
	}
	
	public void walkUpLeft() {
		project.batch.draw(walkLeftAni.getKeyFrame(elapsedTime, true), getX(), getY());
		setPosition(getX() - speed * Gdx.graphics.getDeltaTime(), getY() + speed * Gdx.graphics.getDeltaTime());
	}
	
	public void walkDownRight() {
		project.batch.draw(walkRightAni.getKeyFrame(elapsedTime, true), getX(), getY());
		setPosition(getX() + speed * Gdx.graphics.getDeltaTime(), getY() - speed * Gdx.graphics.getDeltaTime());
	}
	
	public void walkDownLeft() {
		project.batch.draw(walkLeftAni.getKeyFrame(elapsedTime, true), getX(), getY());
		setPosition(getX() - speed * Gdx.graphics.getDeltaTime(), getY() - speed * Gdx.graphics.getDeltaTime());
	}
	
	public void attackUp1() {
		project.batch.draw(attackUpAni1.getKeyFrame(elapsedTime, true), getX(), getY());

	}
	
	public void attackUp2() {
		project.batch.draw(attackUpAni2.getKeyFrame(elapsedTime, true), getX(), getY());
	}
	
	public void attackUp3() {
		project.batch.draw(attackUpAni3.getKeyFrame(elapsedTime, true), getX(), getY());
	}
	
	public void attackDown1() {
		project.batch.draw(attackDownAni1.getKeyFrame(elapsedTime, true), getX(), getY());
	}
	
	public void attackDown2() {
		project.batch.draw(attackDownAni2.getKeyFrame(elapsedTime, true), getX(), getY());
	}
	
	public void attackDown3() {
		project.batch.draw(attackDownAni3.getKeyFrame(elapsedTime, true), getX(), getY());
	}
	
	public void attackLeft1() {
		project.batch.draw(attackLeftAni1.getKeyFrame(elapsedTime, true), getX(), getY());
	}
	
	public void attackLeft2() {
		project.batch.draw(attackLeftAni2.getKeyFrame(elapsedTime, true), getX(), getY());
	}
	
	public void attackLeft3() {
		project.batch.draw(attackLeftAni3.getKeyFrame(elapsedTime, true), getX(), getY());
	}
	
	public void attackRight1() {
		project.batch.draw(attackRightAni1.getKeyFrame(elapsedTime, true), getX(), getY());
	}
	
	public void attackRight2() {
		project.batch.draw(attackRightAni2.getKeyFrame(elapsedTime, true), getX(), getY());
	}
	
	public void attackRight3() {
		project.batch.draw(attackRightAni3.getKeyFrame(elapsedTime, true), getX(), getY());
	}

	public State getCurrentState() {
		return currentState;
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}
}

