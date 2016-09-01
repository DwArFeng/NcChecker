package com.dwarfeng.ncc.module.expl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import com.dwarfeng.ncc.module.AbstractNccModuleObject;
import com.dwarfeng.ncc.module.NccModuleManager;
import com.dwarfeng.ncc.module.nc.Code;

/**
 * ���� {@link Scanner} ��ʵ�ֵ����ش����ȡ����
 * @author DwArFeng
 * @since 1.8
 */
public final class ScannerCodeLoader extends AbstractNccModuleObject implements CodeLoader {

	private final Scanner scanner;
	
	/**
	 * ����һ��ɨ��NC�����ȡ����
	 * @param moduleManager ָ����ģ�͹�������
	 * @param in ָ������������
	 * @throws NullPointerException �� <code>moduleManager</code>Ϊ <code>null</code>ʱ��
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
		
		//TODO ���䷽��
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
