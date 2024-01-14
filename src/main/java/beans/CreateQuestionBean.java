package beans;

import java.util.*;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import businessLogic.BLFacadeImplementation;
import configuration.UtilDate;
import domain.Event;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;




@ManagedBean
@SessionScoped
public class CreateQuestionBean {
	
	private UtilDate ud = new UtilDate();

	private List<Event> listaEventos;
	private Event eventoSel;
	private Date fecha;
	private String newQuestion;
	private Float minBet;

	
	
	public CreateQuestionBean() {
		fecha = new Date();
		listaEventos = BLFacadeImplementation.getBlFacade().getEvents(fecha);
	}
	
	public void getEventList(Date fecha) {
		
		setListaEventos(BLFacadeImplementation.getBlFacade().getEvents(fecha));
		
	}
	
	public void addQuestion() {
		try {
			if(eventoSel==null) {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Choose an event to create the question for."));
			}
			else if(newQuestion.isEmpty()) {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("You must write a question."));
			}
			else if(minBet==null||minBet<=0.0) {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("The bet min bet must be greater than zero."));
			}
			else {
				BLFacadeImplementation.getBlFacade().createQuestion(getEventoSel(),this.getNewQuestion(),this.getMinBet());
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Question correctly created."));
			}
		} catch (EventFinished e) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("The selected event has already finished."));
		} catch (QuestionAlreadyExist e) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("The question already exists for the selected event."));
		}
	}
	public void onDateSelect(SelectEvent event) {
		 setFecha((Date)event.getObject());
		 getEventList((Date)event.getObject());
		} 

	public void mostrarEvento() {
		System.out.println("Evento seleccionado: " + eventoSel.toString() + eventoSel.getEventDate());
	}
	
	public String formatDate() {
		return ud.formatDate(getFecha());
	}
	
	public List<Event> getListaEventos() {
		return listaEventos;
	}
	public void setListaEventos(List<Event> listaEventos) {
		this.listaEventos = listaEventos;
	}
	public Event getEventoSel() {
		return eventoSel;
	}
	public void setEventoSel(Event eventoSel) {
		this.eventoSel = eventoSel;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getNewQuestion() {
		return newQuestion;
	}

	public void setNewQuestion(String newQuestion) {
		this.newQuestion = newQuestion;
	}

	public Float getMinBet() {
		return minBet;
	}

	public void setMinBet(Float minBet) {
		this.minBet = minBet;
	}
	
}
