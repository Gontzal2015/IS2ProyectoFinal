package beans;

import java.util.*;
import java.util.logging.Logger;
import java.util.logging.Level;

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

    private static final Logger logger = Logger.getLogger(CreateQuestionBean.class.getName());

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
            if(eventoSel == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Choose an event"));
            } else if(newQuestion.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("You have to write a question"));
            } else if(minBet == null || minBet <= 0.0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("The minimun bet must be greater than 0"));
            } else {
                BLFacadeImplementation.getBlFacade().createQuestion(getEventoSel(), this.getNewQuestion(), this.getMinBet());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("The question has been successfully created"));
            }
        } catch (EventFinished e) {
            logger.log(Level.SEVERE, "El evento ya ha terminado.", e);
        } catch (QuestionAlreadyExist e) {
            logger.log(Level.SEVERE, "Ya existe la pregunta para el evento seleccionado.", e);
        }
    }

    public void onDateSelect(SelectEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Choosen date: " + event.getObject()));
        setFecha((Date) event.getObject());
        getEventList((Date) event.getObject());
    }

    public void mostrarEvento() {
        logger.log(Level.INFO, "Evento seleccionado::::::::::::::::::{0} {1}", new Object[]{eventoSel, eventoSel.getEventDate()});
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
