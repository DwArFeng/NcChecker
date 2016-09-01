package com.dwarfeng.ncc.module.expl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import com.dwarfeng.ncc.module.AbstractNccModuleObject;
import com.dwarfeng.ncc.module.NccModuleManager;
import com.dwarfeng.ncc.module.nc.Code;

/**
 * 利用 {@link Scanner} 来实现的数控代码读取器。
 * @author DwArFeng
 * @since 1.8
 */
public final class ScannerCodeLoader extends AbstractNccModuleObject implements CodeLoader {

	private final Scanner scanner;
	
	/**
	 * 生成一个扫描NC代码读取器。
	 * @param moduleManager 指定的模型管理器。
	 * @param in 指定的输入流。
	 * @throws NullPointerException 当 <code>moduleManager</code>为 <code>null</code>时。
	 */
	public ScannerCodeLoader(NccModuleManager moduleManager,InputStream in) {
		super(moduleManager);
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
	 * @see com.dwarfeng.ncc.module.io.NcCodeLoader#loadNext()
	 */
	@Override
	public Code loadNext() throws IOException {
		
		//TODO 补充方法
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.module.io.NcCodeLoader#hasNext()
	 */
	@Override
	public boolean hasNext() throws IOException {
		return scanner.hasNextLine();
	}

}
