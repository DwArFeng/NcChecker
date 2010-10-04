package com.dwarfeng.ncc.model.mut;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import com.dwarfeng.ncc.model.front.CodeLoader;
import com.dwarfeng.ncc.model.nc.AttrCodeLabel;
import com.dwarfeng.ncc.model.nc.Code;
import com.dwarfeng.ncc.model.nc.CodeLabel;
import com.dwarfeng.ncc.model.nc.StringCode;

/**
 * 从输入流读取代码的代码读取实现类。
 * @author DwArFeng
 * @since 1.8
 */
public final class StreamCodeLoader implements CodeLoader {

	private final Scanner scanner;
	private int lineIndex = 1;
	
	/**
	 * 生成一个扫描NC代码读取器。
	 * @param in 指定的输入流。
	 * @throws NullPointerException 当入口参数为 <code>null</code>时。
	 */
	public StreamCodeLoader(InputStream in) {
		Objects.requireNonNull(in);
		this.scanner = new Scanner(in);
	}

	/*
	 * (non-Javadoc)
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() throws IOException {
		scanner.close();
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb	.append("ScannerNcLoader [scanner = " )
			.append(scanner)
			.append("]");
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.model.io.NcCodeLoader#loadNext()
	 */
	@Override
	public Code loadNext() throws IOException {
		CodeLabel codeLabel = new AttrCodeLabel.Builder().lineNumber(lineIndex++).build();
		return new StringCode(scanner.nextLine(), codeLabel);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.model.io.NcCodeLoader#hasNext()
	 */
	@Override
	public boolean hasNext() throws IOException {
		return scanner.hasNextLine();
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.model.front.CodeLoader#currentValue()
	 */
	@Override
	public int currentValue() {
		return lineIndex;
	}

}
