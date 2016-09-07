package com.dwarfeng.ncc.module.nc;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * 利用数组实现的NC代码列表。
 * @author DwArFeng
 * @since 1.8
 */
public final class ArrayCodeList implements CodeSerial {

	private final Code[] codeArray;
	
	/**
	 * 构造一个由指定数组组成的代码列表。
	 * @param codeArray 指定的数组。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 * @throws 代码不呈颗粒性递增。
	 */
	public ArrayCodeList(Code[] codeArray) {
		Objects.requireNonNull(codeArray);
		this.codeArray = codeArray;
		if(codeArray.length > 2){
			Arrays.sort(this.codeArray, new CodeComparator());
			for(int i = 0 ; i < this.codeArray.length - 2; i ++){
				if(this.codeArray[i].getLabel().getLineIndex() != this.codeArray[i+1].getLabel().getLineIndex() - 1)
					throw new IllegalArgumentException();
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.module.nc.NcCode#getCode(int)
	 */
	@Override
	public Code getCode(int lineIndex) {
		if(lineIndex > getMaxLineNumber() || lineIndex < getMinLineNumber())
			throw new NoSuchElementException();
		return codeArray[lineIndex - getMinLineNumber()];
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb	.append("ArrayNcCode [totleLine = ")
			.append(getTotle())
			.append(" minLine = ")
			.append(getMaxLineNumber())
			.append(" maxLine = ")
			.append(getMaxLineNumber())
			.append("]");
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<Code> iterator() {
		return new Iterator<Code>() {

			private int index = 0;
			
			/*
			 * (non-Javadoc)
			 * @see java.util.Iterator#hasNext()
			 */
			@Override
			public boolean hasNext() {
				return index < getTotle()-1;
			}

			/*
			 * (non-Javadoc)
			 * @see java.util.Iterator#next()
			 */
			@Override
			public Code next() {
				if(index >= getTotle()) throw new NoSuchElementException();
				return codeArray[index++];
			}
			
		};
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.module.nc.CodeSerial#getTotle()
	 */
	@Override
	public int getTotle() {
		return codeArray.length;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.module.nc.CodeSerial#toArray()
	 */
	@Override
	public Code[] toArray() {
		return this.codeArray;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.module.nc.CodeSerial#toArray(int, int)
	 */
	@Override
	public Code[] toArray(int start, int end) {
		if(start > end) throw new IndexOutOfBoundsException();
		if(getMinLineNumber() > start) throw new IndexOutOfBoundsException();
		if(getMaxLineNumber() < end) throw new IndexOutOfBoundsException();
		Code[] codes = new Code[end - start + 1];
		for(int i = 0 ; i < codes.length ; i ++){
			codes[i] = getCode(start + i);
		}
		return codes;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.module.nc.CodeSerial#getMaxLineNumber()
	 */
	@Override
	public int getMaxLineNumber() {
		if(this.codeArray.length == 0) return -1;
		return codeArray[codeArray.length - 1].getLabel().getLineIndex();
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.module.nc.CodeSerial#getMinLineNumber()
	 */
	@Override
	public int getMinLineNumber() {
		if(this.codeArray.length == 0) return -1;
		return codeArray[0].getLabel().getLineIndex();
	}

}
