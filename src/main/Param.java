package main;

import java.io.Serializable;

/**
 * Parameter model class for simple, generically typed (name, value) pairs
 * 
 * @author xwebert
 *
 * @param <T> The generic type of the value
 */
public class Param<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Name of the parameter
	 */
	private String name = null;
	
	/**
	 * Value of the parameter
	 */
	private T value = null;
	
	public Param(String name, T value) {
		this.name = name;
		this.value = value;
	}
	
	/**
	 * Returns the parameter name
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the parameter value
	 * 
	 * @return
	 */
	public T getValue() {
		return value;
	}

	/**
	 * Set the parameter value
	 * 
	 * @param value
	 */
	public void setValue(T value) {
		this.value = value;
	}
	
	/**
	 * Two parameters are equal when they have the same name
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		return name.equals(((Param<T>)obj).name);
	}

	/**
	 * String output
	 * 
	 */
	@Override
	public String toString() {
		return getName() + " = "+ getValue();
	}	
}
