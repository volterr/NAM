package ru.volterr.nam;

public class Constants {
	//message protocols
	public static final String REQUEST_ROUTE="i need new route";
	public static final String INFORM_ROUTE="here's your new route";
	public static final String INFORM_MESSAGE="it's traffic message";
	public static final String INFORM_LINK="new link";
	public static final String REQUEST_SHOW_GUI="show me your gui";
	public static final String REQUEST_PING="ping";
	public static final String CONFIRM_PONG="pong";
	public static final String INFORM_COFFEE = "who did it?";
	public static final String INFORM_STARTMODELING = "prepare for modeling";
	public static final String CONFIRM_TCP = "tcp";
	public static final String CONFIRM_FINISHMODELING = "here is modeling results";
	public static final String REFUSE_DROP = "msg dropped";
	
	//CID
	public static final String NULL_CID="null"; //no conversation purpose
	public static final String CONFIRM_CID="confirm"; //no conversation purpose
	
	//GUI events
	public static final int TEST_GUIEVENT = 0;
	public static final int ADD_ROUTER_GUIEVENT = 1;
	public static final int ADD_USER_GUIEVENT = 2;
	public static final int SHOW_GUI_GUIEVENT = 3;
	public static final int STARTMODELING_GUIEVENT = 4;
	
}
