package ru.volterr.nam.agents;

import jade.core.AID;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.util.Logger;

import java.util.ArrayList;
import java.util.List;

import ru.volterr.nam.behaviours.user.RecvPing;
import ru.volterr.nam.behaviours.user.UserReceiveMsg;
import ru.volterr.nam.behaviours.user.UserStaticGenTraffic;
import ru.volterr.nam.behaviours.user.UserSubscribe;
import ru.volterr.nam.gui.views.UserGUI;

@SuppressWarnings("serial")
public class UserAgent extends GuiAgent {

	private List<AID> subscriptions = new ArrayList<AID>();
	private AID gateway;
	//public int dt = 1000;	- deprecated generation parameter
	
	private Logger log;
	
	private UserGUI gui;
	
	public void setup(){
		//init logger
		log = Logger.getMyLogger(this.getClass().getName());
		
		//init gui
		gui = new UserGUI(this);
		gui.setVisible(false);
		
		//parse arguments
		Object[] args = getArguments();
		if(args!=null && args.length>0)
			try{
				this.setGateway((AID)args[0]);
				if (args[1] != null)
					this.subscriptions.addAll( (List<AID>) args[1] );
			}catch(Exception e){
				log.log(Logger.SEVERE, "Exception:", e);
			}
		
		log.log(Logger.INFO, "I am " + getLocalName() + ". Damn " + getLocalName() + "!");
		
		// Register the user generating service in the yellow pages
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("User");
		sd.setName(getLocalName()+" User");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
		
		addBehaviour(new UserReceiveMsg(this));
		//addBehaviour(new UserStaticGenTraffic(this, dt)); - deprecated, now activates only at modeling procedure
		addBehaviour(new RecvPing());
		
	}
	
	
	protected void takeDown() {
		// Deregister from the yellow pages
		try {
			DFService.deregister(this);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
		
	}
	
	public void startModeling(Long time){
		log.log(Logger.INFO,getLocalName()+"#starts modeling procedure");
		addBehaviour(new UserStaticGenTraffic(this,time));
	}
	
	public void addSubscriptions(List<AID> targets){
		subscriptions.addAll(targets);
	}
	
	public void addSubscription(AID target){
		subscriptions.add(target);
	}
	
	public void removeSubscribers(List<AID> targets){
		subscriptions.removeAll(targets);
	}
	
	public void removeSubscriber(AID target){
		subscriptions.remove(target);
	}


	@Override
	protected void onGuiEvent(GuiEvent ev) {
		// TODO Auto-generated method stub
		
	}
	
	public void showGui(){
		gui.setVisible(true);
		gui.update();
		
	}


	public AID getGateway() {
		return gateway;
	}


	public void setGateway(AID gateway) {
		this.gateway = gateway;
	}
	
	public List<AID> getSubscriptions() {
		return subscriptions;
	}
	

}
