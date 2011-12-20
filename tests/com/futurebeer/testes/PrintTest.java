package com.futurebeer.testes;

import javax.print.DocFlavor;
import javax.print.StreamPrintService;
import javax.print.StreamPrintServiceFactory;

public class PrintTest {
	public static void main(String[] args) {
		DocFlavor flavor = DocFlavor.INPUT_STREAM.GIF;
		String mimeType = DocFlavor.BYTE_ARRAY.TEXT_PLAIN_US_ASCII.getMimeType();
		
		StreamPrintServiceFactory[] factories = StreamPrintServiceFactory.lookupStreamPrintServiceFactories(flavor, mimeType);
		for (StreamPrintServiceFactory streamPrintServiceFactory : factories) {
			StreamPrintService stream = streamPrintServiceFactory.getPrintService(null);
			
			System.out.println(streamPrintServiceFactory);
		}
	}
}
