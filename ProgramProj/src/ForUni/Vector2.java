package ForUni;

/**
 * A 2-dimensional Vector used for calculation of Ballposition etc.
 */
public class Vector2 {
	float X, Y;
	
	/**
	 * Creates a new Vector with given x,y values.
	 * @param X x-value of the vector
	 * @param Y y-value of the vector
	 */
	public Vector2(float X, float Y) {
		this.X = X; this.Y = Y;
	}
	
	/**
	 * Adds another Vector to this one.
	 * @param V other Vector
	 * @return result of adding the 2 Vectors
	 */
	public Vector2 Add(Vector2 V) {
		return new Vector2(X + V.X, Y + V.Y);
	}
	
	/**
	 * Subtracts another Vector from this one.
	 * @param V other Vector
	 * @return result of subtracting the 2 Vectors
	 */
//	public Vector2 Subtract(Vector2 V) {
//		return new Vector2(X - V.X, Y - V.Y);
//	}
	
	/**
	 * Multiplies another Vector to this one.
	 * @param V other Vector
	 * @return result of multiplying the 2 Vectors
	 */
//	public Vector2 Multiply(Vector2 V) {
//		return new Vector2(X * V.X, Y * V.Y);
//	}
	
	/**
	 * Divides this Vector through another Vector.
	 * @param V other Vector
	 * @return result of dividing the 2 Vectors
	 */
//	public Vector2 Divide(Vector2 V) {
//		return new Vector2(X / V.X, Y / V.Y);
//	}
	
	/**
	 * Adds a given value a to both coordinates of the Vector.
	 * @param a value to add to the vector
	 * @return result of adding a to this vector
	 */
//	public Vector2 Add(float a)
//	{
//		return new Vector2(X + a, Y + a);
//	}
	
	/**
	 * Subtracts a given value a from both coordinates of the Vector.
	 * @param a value to subtract from the vector
	 * @return result of subtracting a from this vector
	 */
//	public Vector2 Subtract(float a)
//	{
//		return new Vector2(X - a, Y - a);
//	}
	
	/**
	 * Multiplies the Vector with given value a.
	 * @param a value to multiply with
	 * @return result of multiplication
	 */
	public Vector2 Multiply(float a)
	{
		return new Vector2(X * a, Y * a);
	}
	
	/**
	 * Divides this Vector through given value a.
	 * @param a value to divide through
	 * @return result of division
	 */
	public Vector2 Divide(float a)
	{
		return new Vector2(X / a, Y / a);
	}
	
	/**
	 * Returns the normalized Vector (Length = 1).
	 * @return Vector with length 1.
	 */
	public Vector2 Normalize()
	{
		return new Vector2(X / Length(), Y / Length());
	}
	
	/**
	 * Calculated the Distance from this Vector to another one.
	 * @param V the other Vector
	 * @return the distance to the other vector
	 */
//	public float DistanceTo(Vector2 V) {
//		Vector2 D = new Vector2(V.X - X, V.Y - Y);
//		return D.Length();
//	}
	
	
	/**
	 * Calculated the length of this Vector (from (0,0)).
	 * @return the length of this Vector.
	 */
	public float Length() {
		return (float) Math.sqrt(X * X + Y * Y);
	}
//	
//	public float LengthSquared() {
//		return X * X + Y * Y;
//	}
	
	@Override
	public String toString() {
		return String.valueOf(X) + ", " + String.valueOf(Y);
	}
}