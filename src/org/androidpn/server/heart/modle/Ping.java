package org.androidpn.server.heart.modle;

import org.xmpp.packet.IQ;
/**
 * 
  * @ClassName: Ping
  * @Description: ping the client
  * @author jin
  * @Company 深圳德奥技术有限公司
  * @date 2016-1-5 下午2:16:43
  *
 */
public class Ping extends IQ {
	 public Ping() {
	    }
	    
	    public Ping(String from, String to) {
	        setTo(to);
	        setFrom(from);
	        setType(IQ.Type.get);
	        setID(getID());
	    }
	    
	    public String getChildElementXML() {
	        return "<" + "ping" + " xmlns=\'" + "urn:xmpp:ping" + "\' />";
	    }
}
