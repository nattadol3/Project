package com.game.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.game.project.project;

public class Chalie extends Sprite{
	private float hp, damage, speed, elaspedTime;
	
	private float animationTime = 0f;
	private final float timePerAnimation = 6f;
	
	private float atkTime = 6f;
	
	private Sound attacking, getHit;
	
	private int currentAnimationIndex;
	
	private Animation<TextureRegion> currentAnimation;
	
	private TextureRegion currentFrame;
	
	// 1 mean left-sided, 2 mean right-sided
	private TextureRegion[] sittingIdle1, sitdown1, sittingIdleTwo1, standup1,
						leandown1, munchgrass1, leanup1, walk1;
			
	private TextureRegion[] sittingIdle2, sitdown2, sittingIdleTwo2, standup2,
						leandown2, munchgrass2, leanup2, walk2;
			
	private Animation<TextureRegion> sittingIdleAni1, sitdownAni1, sittingIdleTwoAni1, standupAni1,
						leandownAni1, munchgrassAni1, leanupAni1, walkAni1;
	
	private Animation<TextureRegion> sittingIdleAni2, sitdownAni2, sittingIdleTwoAni2, standupAni2,
						leandownAni2, munchgrassAni2, leanupAni2, walkAni2;
	
	private Array<Animation<TextureRegion>> randomAnimations;
	
	//
	private TextureRegion[][] tmpFrame;
	
	//
	public boolean isMunching;
	
	public Chalie(float hp, float damage, float speed, Texture chalieLeft, Texture chalieRight) {
		this.hp = hp;
		this.damage = damage;
		this.speed = speed;
		
		attacking = Gdx.audio.newSound(Gdx.files.internal("SFX/20_orc_special_atk.wav"));
		
		getHit = Gdx.audio.newSound(Gdx.files.internal("SFX/21_orc_damage_1.wav"));

		tmpFrame = TextureRegion.split(chalieLeft, 64, 64);
		
		//
		int index = 0;
		
		sittingIdle1 = new TextureRegion[8];
		
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 8; j ++) {
				sittingIdle1[index++] = tmpFrame[1][j];
			}
		}
		
		sittingIdleAni1 = new Animation<TextureRegion>(1f / 8f, sittingIdle1);
		sittingIdleAni1.setPlayMode(Animation.PlayMode.LOOP);
		
		index = 0;
		
		//
		sitdown1 = new TextureRegion[3];
		
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 3; j ++) {
				sitdown1[index++] = tmpFrame[2][j];
			}
		}
		
		sitdownAni1 = new Animation<TextureRegion>(1f / 3f, sitdown1);
		
		index = 0;
		
		//
		sittingIdleTwo1 = new TextureRegion[8];
		
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 8; j ++) {
				sittingIdleTwo1[index++] = tmpFrame[3][j];
			}
		}
		
		sittingIdleTwoAni1 = new Animation<TextureRegion>(1f / 8f, sittingIdleTwo1);
		sittingIdleTwoAni1.setPlayMode(Animation.PlayMode.LOOP);

		
		index = 0;
		
		//
		standup1 = new TextureRegion[3];
		
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 3; j ++) {
				standup1[index++] = tmpFrame[4][j];
			}
		}
		
		standupAni1 = new Animation<TextureRegion>(1f / 3f, standup1);
		
		index = 0;
		
		//
		leandown1 = new TextureRegion[4];
		
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 4; j ++) {
				leandown1[index++] = tmpFrame[5][j];
			}
		}
		
		leandownAni1 = new Animation<TextureRegion>(1f / 4f, leandown1);
		leandownAni1.setPlayMode(Animation.PlayMode.NORMAL);
		
		index = 0;
		
		//
		munchgrass1 = new TextureRegion[8];
		
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 8; j ++) {
				munchgrass1[index++] = tmpFrame[6][j];
			}
		}
		
		munchgrassAni1 = new Animation<TextureRegion>(1f / 8f, munchgrass1);
		munchgrassAni1.setPlayMode(Animation.PlayMode.LOOP);

		
		index = 0;
		
		//
		leanup1 = new TextureRegion[4];
		
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 4; j ++) {
				leanup1[index++] = tmpFrame[7][j];
			}
		}
		
		leanupAni1 = new Animation<TextureRegion>(1f / 4f, leanup1);
		leanupAni1.setPlayMode(Animation.PlayMode.NORMAL);

		index = 0;
		
		//
		walk1 = new TextureRegion[8];
		
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 8; j ++) {
				walk1[index++] = tmpFrame[8][j];
			}
		}
		
		walkAni1 = new Animation<TextureRegion>(1f / 8f, walk1);
		
		index = 0;
		
		//
		
		tmpFrame = TextureRegion.split(chalieRight, 64, 64);
		
		//
		sittingIdle2 = new TextureRegion[8];
		
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 8; j ++) {
				sittingIdle2[index++] = tmpFrame[1][j + 1];
			}
		}
		
		sittingIdleAni2 = new Animation<TextureRegion>(1f / 8f, sittingIdle2);
		
		index = 0;
		
		//
		sitdown2 = new TextureRegion[3];
		
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 3; j ++) {
				sitdown2[index++] = tmpFrame[2][j + 1];
			}
		}
		
		sitdownAni2 = new Animation<TextureRegion>(1f / 3f, sitdown2);
		
		index = 0;
		
		//
		sittingIdleTwo2 = new TextureRegion[8];
		
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 8; j ++) {
				sittingIdleTwo2[index++] = tmpFrame[3][j + 1];
			}
		}
		
		sittingIdleTwoAni2 = new Animation<TextureRegion>(1f / 8f, sittingIdleTwo2);
		
		index = 0;
		
		//
		standup2 = new TextureRegion[3];
		
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 3; j ++) {
				standup2[index++] = tmpFrame[4][j + 1];
			}
		}
		
		standupAni2 = new Animation<TextureRegion>(1f / 3f, standup2);
		
		index = 0;
		
		//
		leandown2 = new TextureRegion[4];
		
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 4; j ++) {
				leandown2[index++] = tmpFrame[5][j + 1];
			}
		}
		
		leandownAni2 = new Animation<TextureRegion>(1f / 4f, leandown2);
		
		index = 0;
		
		//
		munchgrass2 = new TextureRegion[8];
		
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 8; j ++) {
				munchgrass2[index++] = tmpFrame[6][j + 1];
			}
		}
		
		munchgrassAni2 = new Animation<TextureRegion>(1f / 8f, munchgrass2);
		
		index = 0;
		
		//
		leanup2 = new TextureRegion[4];
		
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 4; j ++) {
				leanup2[index++] = tmpFrame[7][j + 1];
			}
		}
		
		munchgrassAni2 = new Animation<TextureRegion>(1f / 4f, leanup2);
		
		index = 0;
		
		//
		walk2 = new TextureRegion[8];
		
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 8; j ++) {
				walk2[index++] = tmpFrame[8][j + 1];
			}
		}
		
		walkAni2 = new Animation<TextureRegion>(1f / 8f, walk2);
		
		randomAnimations = new Array<>();
		randomAnimations.add(leandownAni1);
		//randomAnimations.add(leandownAni2);
		randomAnimations.add(leanupAni1);
		//randomAnimations.add(leanupAni2);
		randomAnimations.add(munchgrassAni1);
		//randomAnimations.add(munchgrassAni2);
		//randomAnimations.add(sitdownAni1);
		//randomAnimations.add(sitdownAni2);
		randomAnimations.add(sittingIdleAni1);
		//randomAnimations.add(sittingIdleAni2);
		randomAnimations.add(sittingIdleTwoAni1);;
		//randomAnimations.add(sittingIdleTwoAni2);
		//randomAnimations.add(standupAni1);
		//randomAnimations.add(standupAni2);
		//randomAnimations.add(walkAni1);
		//randomAnimations.add(walkAni2);
		
		 currentAnimationIndex = MathUtils.random(4);
	     currentAnimation = randomAnimations.get(currentAnimationIndex);
	}
	
	public void update(float delta) {
		elaspedTime += delta;
		atkTime += delta;
        
        if (elaspedTime > timePerAnimation) {
            elaspedTime = 0f;
            currentAnimationIndex = MathUtils.random(4);
            currentAnimation = randomAnimations.get(currentAnimationIndex);
        }
	}
	
	public void draw() {
		isMunching = false;
		currentFrame = currentAnimation.getKeyFrame(elaspedTime);
		
			
		if (currentAnimation == munchgrassAni1) {
			isMunching = true;
			if (atkTime > timePerAnimation) {
				atkTime = -6f;
				attacking.play(0.2f);
			} else {
				atkTime += Gdx.graphics.getDeltaTime();
			}
		}
		project.batch.draw(currentFrame, getX(), getY());
	}
	
	public void getHit(float damage) {
		hp -= damage;
	}

	public float getDamage() {
		return damage;
	}
	
	public void died() {
		setPosition(2000, 2000);
	}

	public void setDamage(float damage) {
		this.damage = damage;
	}

	public float getHp() {
		return hp;
	}

	public void setHp(float hp) {
		this.hp = hp;
	}
}


















