package it.nobusware.client.utils;

import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3i;

public class Vec3d {
  public static final Vec3d ZERO = new Vec3d(0.0D, 0.0D, 0.0D);
  
  public final double xCoord;
  
  public final double yCoord;
  
  public final double zCoord;
  
  public Vec3d(double x, double y2, double z) {
    if (x == -0.0D)
      x = 0.0D; 
    if (y2 == -0.0D)
      y2 = 0.0D; 
    if (z == -0.0D)
      z = 0.0D; 
    this.xCoord = x;
    this.yCoord = y2;
    this.zCoord = z;
  }
  
  public Vec3d(Vec3i vector) {
    this(vector.getX(), vector.getY(), vector.getZ());
  }
  
  public Vec3d subtractReverse(Vec3d vec) {
    return new Vec3d(vec.xCoord - this.xCoord, vec.yCoord - this.yCoord, vec.zCoord - this.zCoord);
  }
  
  public Vec3d normalize() {
    double d0 = Math.sqrt(this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord);
    return (d0 < 1.0E-4D) ? ZERO : new Vec3d(this.xCoord / d0, this.yCoord / d0, this.zCoord / d0);
  }
  
  public double dotProduct(Vec3d vec) {
    return this.xCoord * vec.xCoord + this.yCoord * vec.yCoord + this.zCoord * vec.zCoord;
  }
  
  public Vec3d crossProduct(Vec3d vec) {
    return new Vec3d(this.yCoord * vec.zCoord - this.zCoord * vec.yCoord, this.zCoord * vec.xCoord - this.xCoord * vec.zCoord, this.xCoord * vec.yCoord - this.yCoord * vec.xCoord);
  }
  
  public Vec3d subtract(Vec3d vec) {
    return subtract(vec.xCoord, vec.yCoord, vec.zCoord);
  }
  
  public Vec3d subtract(double x, double y2, double z) {
    return addVector(-x, -y2, -z);
  }
  
  public Vec3d add(Vec3d vec) {
    return addVector(vec.xCoord, vec.yCoord, vec.zCoord);
  }
  
  public Vec3d addVector(double x, double y2, double z) {
    return new Vec3d(this.xCoord + x, this.yCoord + y2, this.zCoord + z);
  }
  
  public double distanceTo(Vec3d vec) {
    double d0 = vec.xCoord - this.xCoord;
    double d2 = vec.yCoord - this.yCoord;
    double d3 = vec.zCoord - this.zCoord;
    return Math.sqrt(d0 * d0 + d2 * d2 + d3 * d3);
  }
  
  public double squareDistanceTo(Vec3d vec) {
    double d0 = vec.xCoord - this.xCoord;
    double d2 = vec.yCoord - this.yCoord;
    double d3 = vec.zCoord - this.zCoord;
    return d0 * d0 + d2 * d2 + d3 * d3;
  }
  
  public double squareDistanceTo(double xIn, double yIn, double zIn) {
    double d0 = xIn - this.xCoord;
    double d2 = yIn - this.yCoord;
    double d3 = zIn - this.zCoord;
    return d0 * d0 + d2 * d2 + d3 * d3;
  }
  
  public Vec3d scale(double p_186678_1_) {
    return new Vec3d(this.xCoord * p_186678_1_, this.yCoord * p_186678_1_, this.zCoord * p_186678_1_);
  }
  
  public double lengthVector() {
    return Math.sqrt(this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord);
  }
  
  public double lengthSquared() {
    return this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord;
  }
  
  public Vec3d getIntermediateWithXValue(Vec3d vec, double x) {
    double d0 = vec.xCoord - this.xCoord;
    double d2 = vec.yCoord - this.yCoord;
    double d3 = vec.zCoord - this.zCoord;
    if (d0 * d0 < 1.0000000116860974E-7D)
      return null; 
    double d4 = (x - this.xCoord) / d0;
    return (d4 >= 0.0D && d4 <= 1.0D) ? new Vec3d(this.xCoord + d0 * d4, this.yCoord + d2 * d4, this.zCoord + d3 * d4) : null;
  }
  
  public Vec3d getIntermediateWithYValue(Vec3d vec, double y2) {
    double d0 = vec.xCoord - this.xCoord;
    double d2 = vec.yCoord - this.yCoord;
    double d3 = vec.zCoord - this.zCoord;
    if (d2 * d2 < 1.0000000116860974E-7D)
      return null; 
    double d4 = (y2 - this.yCoord) / d2;
    return (d4 >= 0.0D && d4 <= 1.0D) ? new Vec3d(this.xCoord + d0 * d4, this.yCoord + d2 * d4, this.zCoord + d3 * d4) : null;
  }
  
  public Vec3d getIntermediateWithZValue(Vec3d vec, double z) {
    double d0 = vec.xCoord - this.xCoord;
    double d2 = vec.yCoord - this.yCoord;
    double d3 = vec.zCoord - this.zCoord;
    if (d3 * d3 < 1.0000000116860974E-7D)
      return null; 
    double d4 = (z - this.zCoord) / d3;
    return (d4 >= 0.0D && d4 <= 1.0D) ? new Vec3d(this.xCoord + d0 * d4, this.yCoord + d2 * d4, this.zCoord + d3 * d4) : null;
  }
  
  public boolean equals(Object p_equals_1_) {
    if (this == p_equals_1_)
      return true; 
    if (!(p_equals_1_ instanceof Vec3d))
      return false; 
    Vec3d vec3d = (Vec3d)p_equals_1_;
    return (Double.compare(vec3d.xCoord, this.xCoord) == 0 && Double.compare(vec3d.yCoord, this.yCoord) == 0 && 
      Double.compare(vec3d.zCoord, this.zCoord) == 0);
  }
  
  public int hashCode() {
    long j = Double.doubleToLongBits(this.xCoord);
    int i = (int)(j ^ j >>> 32L);
    j = Double.doubleToLongBits(this.yCoord);
    i = 31 * i + (int)(j ^ j >>> 32L);
    j = Double.doubleToLongBits(this.zCoord);
    i = 31 * i + (int)(j ^ j >>> 32L);
    return i;
  }
  
  public String toString() {
    return "(" + this.xCoord + ", " + this.yCoord + ", " + this.zCoord + ")";
  }
  
  public Vec3d rotatePitch(float pitch) {
    float f = MathHelper.cos(pitch);
    float f2 = MathHelper.sin(pitch);
    double d0 = this.xCoord;
    double d2 = this.yCoord * f + this.zCoord * f2;
    double d3 = this.zCoord * f - this.yCoord * f2;
    return new Vec3d(d0, d2, d3);
  }
  
  public Vec3d rotateYaw(float yaw) {
    float f = MathHelper.cos(yaw);
    float f2 = MathHelper.sin(yaw);
    double d0 = this.xCoord * f + this.zCoord * f2;
    double d2 = this.yCoord;
    double d3 = this.zCoord * f - this.xCoord * f2;
    return new Vec3d(d0, d2, d3);
  }
  
  public static Vec3d fromPitchYaw(float p_189986_0_, float p_189986_1_) {
    float f = MathHelper.cos(-p_189986_1_ * 0.017453292F - 3.1415927F);
    float f2 = MathHelper.sin(-p_189986_1_ * 0.017453292F - 3.1415927F);
    float f3 = -MathHelper.cos(-p_189986_0_ * 0.017453292F);
    float f4 = MathHelper.sin(-p_189986_0_ * 0.017453292F);
    return new Vec3d((f2 * f3), f4, (f * f3));
  }
}
