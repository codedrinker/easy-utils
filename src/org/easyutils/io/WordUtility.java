package org.easyutils.io;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class WordUtility {
	public static String fileToWrite = FileUtility.getRelativeFilePath("des",
			"fileToWrite.doc");

	public static void main(String[] args) throws Exception {

		String content = "测试数据,将被写入文档";
		byte b[] = content.getBytes();
		ByteArrayInputStream bais = new ByteArrayInputStream(b);
		POIFSFileSystem fs = new POIFSFileSystem();
		FileOutputStream ostream = new FileOutputStream(fileToWrite);
		fs.writeFilesystem(ostream);
		bais.close();
		ostream.close();
	}

	public void printWord(HttpServletResponse response) {
		String URL = "C:\\test.doc";
		File file = new File(URL);
		try {
			FileInputStream in = new FileInputStream(file);
			POIFSFileSystem pfs = new POIFSFileSystem(in);
			HWPFDocument hwpf = new HWPFDocument(pfs);
			Range range = hwpf.getRange();
			String str = "woshi 底向阳";
			System.out.println(str);
			// str = StringKit.getISO8859ToGBK(str);
			range.insertBefore(str);
			response.setContentType("application/ms-word");
			response.setHeader("Content-disposition",
					"attachment; filename=2.doc");
			OutputStream out = response.getOutputStream();
			hwpf.write(out);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
