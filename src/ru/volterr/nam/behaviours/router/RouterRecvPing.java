package ru.volterr.nam.behaviours.router;

import ru.volterr.nam.Constants;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.Logger;

public class RouterRecvPing extends CyclicBehaviour {

	MessageTemplate mt = MessageTemplate.MatchProtocol(Constants.REQUEST_PING);
	private Logger log;
	
	public RouterRecvPing() {
		super();
		log = Logger.getMyLogger(this.getClass().getName());
	}
	
	@Override
	public void action() {
		ACLMessage ping = myAgent.receive(mt);
		if(ping != null){
			log.log(Logger.INFO,myAgent.getLocalName() + "#ping received from "+ping.getSender().getLocalName());
			ACLMessage pong = ping.createReply();
			pong.setPerformative(ACLMessage.CONFIRM);
			pong.setProtocol(Constants.CONFIRM_PONG);
			myAgent.send(pong);
		}
		block();
	}

}
