package com.dwarfeng.ncc.model.mut;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Objects;

import com.dwarfeng.ncc.model.front.CodePrinter;
import com.dwarfeng.ncc.model.nc.Code;
import com.dwarfeng.ncc.model.nc.CodeSerial;

/**
 * 向输出流打印代码的代码打印实现类。
 * @author DwArFeng
 * @since 1.8
 */
public class StreamCodePrinter implements CodePrinter {
	
	private final Code[] codes;
	private final OutputStream out;
	private final Charset charset;
	
	private int index;

	/**
	 * 生成一个默认的流代码打印对象。
	 * @param codeSerial 指定的代码序列。
	 * @param out 指定的输出流。
	 * @throws NullPointerException 任何一个入口参数为 <code>null</code>。
	 */
	public StreamCodePrinter(CodeSerial codeSerial, OutputStream out) {
		this(codeSerial, out, Charset.defaultCharset());
	}
	
	/**
	 * 生成一个流代码打印对象。
	 * @param codeSerial 指定的代码序列。
	 * @param out 指定的输出流。
	 * @param charset 指定的字符集。
	 * @throws NullPointerException 任何一个入口参数为 <code>null</code>。
	 */
	public StreamCodePrinter(CodeSerial codeSerial, OutputStream out, Charset charset) {
		Objects.requireNonNull(codeSerial);
		Objects.requireNonNull(out);
		Objects.requireNonNull(charset);
		
		this.codes = codeSerial.toArray();
		this.out = out;
		this.charset = charset;
		this.index = 0;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.module.front.CodePrinter#getTotleCode()
	 */
	@Override
	public int getTotleCode() {
		return codes.length;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.module.front.CodePrinter#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return index < codes.length;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.module.front.CodePrinter#saveNext()
	 */
	@Override
	public void printNext() throws IOException {
		String str = codes[index++].content() + "\n";
		out.write(str.getBytes(charset));
		out.flush();
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.module.front.CodePrinter#currentValue()
	 */
	@Override
	public int currentValue() {
		return index;
	}

}
