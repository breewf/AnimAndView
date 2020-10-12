package com.hy.animandview.bean;

/**
 * @author hy
 * @date 2020/10/12
 * desc:
 **/
public class Particle {

    public float x;
    public float y;
    public float radius;
    public float speed;
    public int alpha;

    /**
     * 最大移动距离
     */
    public float maxOffset = 300f;

    /**
     * 当前移动距离
     */
    public float offset;
    /**
     * 粒子角度
     */
    public double angle;

    public Particle(float x, float y, float radius, float speed, int alpha) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.speed = speed;
        this.alpha = alpha;
    }

    public Particle(float x, float y, float radius, float speed, int alpha,
                    float offset, double angle) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.speed = speed;
        this.alpha = alpha;
        this.offset = offset;
        this.angle = angle;
    }

    public Particle(float x, float y, float radius, float speed, int alpha,
                    float offset, double angle, float maxOffset) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.speed = speed;
        this.alpha = alpha;
        this.offset = offset;
        this.angle = angle;
        this.maxOffset = maxOffset;
    }
}
