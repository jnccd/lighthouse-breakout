package ForUni;

public class Vector2 {
	float X, Y;
	
	public Vector2(float X, float Y) {
		this.X = X; this.Y = Y;
	}
	
	public Vector2 Add(Vector2 V) {
		return new Vector2(X + V.X, Y + V.Y);
	}
	public Vector2 Subtract(Vector2 V) {
		return new Vector2(X - V.X, Y - V.Y);
	}
	public Vector2 Multiply(Vector2 V) {
		return new Vector2(X * V.X, Y * V.Y);
	}
	public Vector2 Divide(Vector2 V) {
		return new Vector2(X / V.X, Y / V.Y);
	}
	public Vector2 Add(float a)
	{
		return new Vector2(X + a, Y + a);
	}
	public Vector2 Subtract(float a)
	{
		return new Vector2(X - a, Y - a);
	}
	public Vector2 Multiply(float a)
	{
		return new Vector2(X * a, Y * a);
	}
	public Vector2 Divide(float a)
	{
		return new Vector2(X / a, Y / a);
	}
	
	public Vector2 Normalize()
	{
		return new Vector2(X / Length(), Y / Length());
	}
	
	public float DistanceTo(Vector2 V) {
		Vector2 D = new Vector2(V.X - X, V.Y - Y);
		return D.Length();
	}
	public float Length() {
		return (float)java.lang.Math.sqrt((double)(X * X + Y * Y));
	}
	public float LengthSquared() {
		return X * X + Y * Y;
	}
	
	@Override
	public String toString() {
		return String.valueOf(X) + ", " + String.valueOf(Y);
	}
}