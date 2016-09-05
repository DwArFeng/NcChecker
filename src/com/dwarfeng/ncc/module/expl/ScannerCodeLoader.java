package com.dwarfeng.ncc.module.expl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import com.dwarfeng.ncc.module.nc.AttrCodeLabel;
import com.dwarfeng.ncc.module.nc.Code;
import com.dwarfeng.ncc.module.nc.CodeLabel;
import com.dwarfeng.ncc.module.nc.StringCode;

/**
 * ���� {@link Scanner} ��ʵ�ֵ����ش����ȡ����
 * @author DwArFeng
 * @since 1.8
 */
public final class ScannerCodeLoader implements CodeLoader {

	private final Scanner scanner;
	private int lineIndex = 1;
	
	/**
	 * ����һ��ɨ��NC�����ȡ����
	 * @param in ָ������������
	 * @throws NullPointerException ����ڲ���Ϊ <code>null</code>ʱ��
	 */
	public ScannerCodeLoader(InputStream in) {
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
		CodeLabel codeLabel = new AttrCodeLabel.Builder().lineNumber(lineIndex++).build();
		return new StringCode(scanner.nextLine(), codeLabel);
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
