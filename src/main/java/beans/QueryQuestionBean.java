package beans;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.*;

import org.primefaces.event.SelectEvent;

import businessLogic.BLFacadeImplementation;
import domain.*;

public class QueryQuestionBean {
	public Date fecha;
	public Event eventSel;
	public List<Event> listaEventos;
	public Question preguntaSel;

	public List<Question> listaPreguntas;
	
	public QueryQuestionBean() {
		fecha=new Date();
		listaEventos = BLFacadeImplementation.getBlFacade().getEvents(fecha);
		listaPreguntas= new ArrayList<Question>();
	}
	
	public void onDateSelect(SelectEvent event) {
		 FacesContext.getCurrentInstance().addMessage(null,
		 new FacesMessage("Choosen date: "+event.getObject()));
//		 setFecha((Date)event.getObject());
		 getEventList();
		}
	
	public void onEventSelect() {
		getQuestionList();
	}
	
	public void getQuestionList() {
		setListaPreguntas(BLFacadeImplementation.getBlFacade().getEventQuestions(eventSel));
	}
	
	public void getEventList() {
		setListaEventos(BLFacadeImplementation.getBlFacade().getEvents(fecha));
	}
	
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Event getEventSel() {
		return eventSel;
	}
	public void setEventSel(Event eventSel) {
		this.eventSel = eventSel;
	}
	public List<Event> getListaEventos() {
		return listaEventos;
	}
	public void setListaEventos(List<Event> listaEventos) {
		this.listaEventos = listaEventos;
	}

	public List<Question> getListaPreguntas() {
		return listaPreguntas;
	}

	public void setListaPreguntas(List<Question> listaPreguntas) {
		this.listaPreguntas = listaPreguntas;
	}
	
	public Question getPreguntaSel() {
		return preguntaSel;
	}

	public void setPreguntaSel(Question preguntaSel) {
		this.preguntaSel = preguntaSel;
	}


}
