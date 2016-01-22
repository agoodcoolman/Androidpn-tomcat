package org.androidpn.server.heart.modle;

import org.xmpp.packet.IQ;
/**
 * 
  * @ClassName: Pong
  * @Description: response the client ping.
  * @author jin
  * @Company 深圳德奥技术有限公司
  * @date 2016-1-5 下午2:13:57
  *
 */
public class Pong extends IQ {
	/**
     * Composes a Pong packet from a received ping packet. This basically swaps
     * the 'from' and 'to' attributes. And sets the IQ type to result.
     * 
     * @param ping
     */
    public Pong(Ping ping) {
        setType(IQ.Type.result);
        setFrom(ping.getTo());
        setTo(ping.getFrom());
        setID(ping.getID());
    }
    
    /*
     * Returns the child element of the Pong reply, which is non-existent. This
     * is why we return 'null' here. See e.g. Example 11 from
     * http://xmpp.org/extensions/xep-0199.html#e2e
     */
    public String getChildElementXML() {
        return null;
    }
}
