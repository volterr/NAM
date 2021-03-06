package ru.volterr.nam.behaviours.user;

import jade.lang.acl.ACLMessage;
import jade.util.Logger;

import java.io.IOException;
import java.util.Random;

import ru.volterr.nam.Constants;
import ru.volterr.nam.agents.UserAgent;

@SuppressWarnings("serial")
public class UserStaticGenTraffic extends UserGenTraffic{


	private Random rand = new Random();
	private double sprob = 0.7;
	private Logger log;
	
	
	public UserStaticGenTraffic(UserAgent a,long time) {
		super(a,time);
		//init logger
		log = Logger.getMyLogger(this.getClass().getName());
	}
	public UserStaticGenTraffic(UserAgent a,long time,double sprob) {
		super(a,time);
		this.sprob = sprob;
		//init logger
		log = Logger.getMyLogger(this.getClass().getName());
	}
	
	//generates and sends messages
	@Override
	void generate() {
		if(rand.nextDouble()<sprob){
			try {
				int i = rand.
						nextInt(myUser.
						getReceivers().
						size());
				ACLMessage msgL3 = new ACLMessage(ACLMessage.INFORM);
				msgL3.setContentObject(new Integer(1));
				msgL3.addReceiver(myUser.getReceivers().get(i));
				msgL3.setSender(myUser.getAID());
				
				ACLMessage msgL2 = new ACLMessage(ACLMessage.INFORM);
				msgL2.setContentObject(msgL3);
				msgL2.setConversationId(Constants.NULL_CID);
				msgL2.addReceiver(myUser.getGateway());
				msgL2.setProtocol(Constants.INFORM_MESSAGE);
				
				log.log(Logger.INFO,myUser.getLocalName() + "# sended message to " + myUser.getReceivers().get(i).getLocalName());
				myUser.send(msgL2);
			} catch (IOException e) {
				log.log(Logger.SEVERE,"problems with output stream exception: ",e);
			} catch(IllegalArgumentException e){
				log.log(Logger.WARNING, "User " + myUser.getLocalName() + " must have at least one destination user to generate traffic", e);
				block();
			} catch (Exception e){
				log.log(Logger.SEVERE,"Exception",e);
			}
		}
	}

}
