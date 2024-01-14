package beans;

import java.util.*;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import businessLogic.BLFacadeImplementation;
import domain.Event;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;




@ManagedBean
@SessionScoped
public class CreateQuestionBean {
	

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
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Elige un evento"));
			}
			else if(newQuestion.isEmpty()) {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Tienes que escribir una pregunta"));
			}
			else if(minBet==null||minBet<=0.0) {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("El importe minimo debe ser mas de 0"));
			}
			else BLFacadeImplementation.getBlFacade().createQuestion(getEventoSel(),this.getNewQuestion(),this.getMinBet());
		} catch (EventFinished e) {
			System.out.println("El evento ya ha terminado.");
			e.printStackTrace();
		} catch (QuestionAlreadyExist e) {
			System.out.println("Ya existe la pregunta para el evento seleccionado.");
			e.printStackTrace();
		}
	}
	public void onDateSelect(SelectEvent event) {
		 FacesContext.getCurrentInstance().addMessage(null,
		 new FacesMessage("Fecha escogida: "+event.getObject()));
		 setFecha((Date)event.getObject());
		 getEventList((Date)event.getObject());
		} 

	public void mostrarEvento() {
		System.out.println("Evento seleccionado::::::::::::::::::" + eventoSel.toString() + eventoSel.getEventDate());
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
