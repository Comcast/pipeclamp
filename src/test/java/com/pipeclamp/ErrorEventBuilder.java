package com.pipeclamp;

//import java.util.Date;

//import com.comcast.xbi.core.CoreHeader;
//import com.comcast.xbi.datatype.VER;
//import com.comcast.xbi.error.ErrorEvent;
//import com.comcast.xbi.error.ErrorLevel;

/**
 *
 * @author Brian Remedios
 */
public class ErrorEventBuilder {

//	private static final VER SchemaVersion = new VER(new byte[] {1} );
//
	public static final String[] ValidHostNames	= new String[] { "somewhere.comcast.com", "tunaforest", "127.0.0.1" };
	public static final String[] InvalidHostNames	= new String[] { "brian's machine", "123 234 54", "billgates@microsoft.com", "*" };
//
//	private ErrorEventBuilder() { }
//
//	public static ErrorEvent errorFor(CoreHeader header) {
//
//		return ErrorEvent.newBuilder()
//				.setHeader(header)
//				.setMessage("something happened!")
//				.setCausedBy("a solar flare")
//				.setCode("ouch!")
//				.setLevel(ErrorLevel.Debug)
//				.build();
//	}
//
//	public static ErrorEvent newErrorEvent(String hostName, String partner) {
//
//		CoreHeader header = CoreHeader.newBuilder()
//				.setHostname(hostName)
//				.setPartner(partner)
//				.setTimestamp(new Date().getTime())
//				.setVersionNum(SchemaVersion)
//				.build();
//
//		return errorFor(header);
//	}

}
