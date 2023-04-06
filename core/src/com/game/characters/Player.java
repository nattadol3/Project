package com.game.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.project.project;

public class Player extends Sprite{
	// HP, movement speed value
	private float hp, speed;
		
	// Character sprite sheet
	private TextureRegion[] walkUp, walkDown, walkLeft, walkRight,
						idleRight, idleLeft, idleUp, idleDown, 
						attackUp, attackDown, attackLeft, attackRight;
	
	// Character animation
	private Animation<TextureRegion> walkUpAni, walkDownAni, walkLeftAni, walkRightAni,
								idleUpAni, idleDownAni, idleLeftAni, idleRightAni,
								attackUpAni, attackDownAni, attackLeftAni, attackRightAni;
		
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
	}
		
	// Current character state
	private State currentState;

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
	}

	public void update(float delta) {
		elapsedTime += delta;
	}
	
	public void draw() {
		boolean isMoving = false;
		
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
		}
		else {
		    isMoving = false;
		    if (currentState == State.WALK_UP)
		        currentState = State.IDLE_UP;
		    else if (currentState == State.WALK_LEFT)
		        currentState = State.IDLE_LEFT;
		    else if (currentState == State.WALK_LEFT_UP)
		        currentState = State.IDLE_LEFT;
		    else if (currentState == State.WALK_LEFT_DOWN)
		        currentState = State.IDLE_LEFT;
		    else if (currentState == State.WALK_RIGHT)
		        currentState = State.IDLE_RIGHT;
		    else if (currentState == State.WALK_RIGHT_UP)
		        currentState = State.IDLE_RIGHT;
		    else if (currentState == State.WALK_RIGHT_DOWN)
		        currentState = State.IDLE_RIGHT;
		    else if (currentState == State.WALK_DOWN)
		        currentState = State.IDLE_DOWN;
		        
		    if (currentState == State.IDLE_UP)
		        idleUp();
		    else if (currentState == State.IDLE_LEFT)
		        idleLeft();
		    else if (currentState == State.IDLE_RIGHT)
		        idleRigt();
		    else if (currentState == State.IDLE_DOWN)
		        idleDown();

		}
	    
	    if (isMoving) {
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

	public State getCurrentState() {
		return currentState;
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}
}

