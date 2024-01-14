package dataAccess;

import java.io.File;
import java.util.ArrayList;
//hello
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;
import org.hibernate.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

//import configuration.ConfigXML;
import configuration.UtilDate;
import configuration.HibernateUtil;
import domain.Event;
import domain.Question;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the data access to the objectDb database
 */
  public class DataAccess implements DataAccessInterface {

	public DataAccess() {
		this.initializeDB();
	}

	/**
	 * This is the data access method that initializes the database with some events
	 * and questions. This method is invoked by the business logic (constructor of
	 * BLFacadeImplementation) when the option "initialize" is declared in the tag
	 * dataBaseOpenMode of resources/config.xml file
	 */
	public void initializeDB() {

		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.beginTransaction();
		try {

			Calendar today = Calendar.getInstance();

			int month = today.get(Calendar.MONTH);
			month += 1;
			int year = today.get(Calendar.YEAR);
			if (month == 12) {
				month = 0;
				year += 1;
			}

			Event ev1 = new Event(1, "Atletico-Athletic", UtilDate.newDate(year, month, 17));
			Event ev2 = new Event(2, "Eibar-Barcelona", UtilDate.newDate(year, month, 17));
			Event ev3 = new Event(3, "Getafe-Celta", UtilDate.newDate(year, month, 17));
			Event ev4 = new Event(4, "Alaves-Deportivo", UtilDate.newDate(year, month, 17));
			Event ev5 = new Event(5, "Espanol-Villareal", UtilDate.newDate(year, month, 17));
			Event ev6 = new Event(6, "Las Palmas-Sevilla", UtilDate.newDate(year, month, 17));
			Event ev7 = new Event(7, "Malaga-Valencia", UtilDate.newDate(year, month, 17));
			Event ev8 = new Event(8, "Girona-Leganes", UtilDate.newDate(year, month, 17));
			Event ev9 = new Event(9, "Real Sociedad-Levante", UtilDate.newDate(year, month, 17));
			Event ev10 = new Event(10, "Betis-Real Madrid", UtilDate.newDate(year, month, 17));

			Event ev11 = new Event(11, "Atletico-Athletic", UtilDate.newDate(year, month, 1));
			Event ev12 = new Event(12, "Eibar-Barcelona", UtilDate.newDate(year, month, 1));
			Event ev13 = new Event(13, "Getafe-Celta", UtilDate.newDate(year, month, 1));
			Event ev14 = new Event(14, "Alaves-Deportivo", UtilDate.newDate(year, month, 1));
			Event ev15 = new Event(15, "Espanol-Villareal", UtilDate.newDate(year, month, 1));
			Event ev16 = new Event(16, "Las Palmas-Sevilla", UtilDate.newDate(year, month, 1));

			Event ev17 = new Event(17, "Malaga-Valencia", UtilDate.newDate(year, month, 28));
			Event ev18 = new Event(18, "Girona-Leganes", UtilDate.newDate(year, month, 28));
			Event ev19 = new Event(19, "Real Sociedad-Levante", UtilDate.newDate(year, month, 28));
			Event ev20 = new Event(20, "Betis-Real Madrid", UtilDate.newDate(year, month, 28));

			Question q1;
			Question q2;
			Question q3;
			Question q4;
			Question q5;
			Question q6;


			q1 = ev1.addQuestion("Who will win the match?", 1);
			q2 = ev1.addQuestion("Who will score first?", 2);
			q3 = ev11.addQuestion("Who will win the match?", 1);
			q4 = ev11.addQuestion("How many goals will be scored in the match?", 2);
			q5 = ev17.addQuestion("Who will win the match?", 1);
			q6 = ev17.addQuestion("Will there be goals in the first half?", 2);


			s.save(ev1);
			s.save(ev2);
			s.save(ev3);
			s.save(ev4);
			s.save(ev5);
			s.save(ev6);
			s.save(ev7);
			s.save(ev8);
			s.save(ev9);
			s.save(ev10);
			s.save(ev11);
			s.save(ev12);
			s.save(ev13);
			s.save(ev14);
			s.save(ev15);
			s.save(ev16);
			s.save(ev17);
			s.save(ev18);
			s.save(ev19);
			s.save(ev20);
			
			s.save(q1);
			s.save(q2);
			s.save(q3);
			s.save(q4);
			s.save(q5);
			s.save(q6);

			s.getTransaction().commit();
			System.out.println("Db initialized");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method creates a question for an event, with a question text and the
	 * minimum bet
	 * 
	 * @param event      to which question is added
	 * @param question   text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws QuestionAlreadyExist if the same question already exists for the
	 *                              event
	 */
	public Question createQuestion(Event event, String question, float betMinimum) throws QuestionAlreadyExist {
		System.out.println(">> DataAccess: createQuestion=> event= " + event + " question= " + question + " betMinimum="
				+ betMinimum);
		
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.beginTransaction();
		
		Query query = s.createQuery("from Event where eventNumber=:eventNum");
		query.setParameter("eventNum", event.getEventNumber());
		Event ev = (Event) query.list().get(0);

		if (ev.DoesQuestionExists(question))
			throw new QuestionAlreadyExist("Ya existe esta pregunta");

		Question q = ev.addQuestion(question, betMinimum);
		s.persist(q);
		s.getTransaction().commit();
		return q;

	}

	/**
	 * This method retrieves from the database the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	public List<Event> getEvents(Date date) {
		System.out.println(">> DataAccess: getEvents");
		
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.beginTransaction();
		
		Query query = s.createQuery("FROM Event WHERE eventDate=:d");
		query.setParameter("d", date);
		List<Event> events = query.list();
		s.getTransaction().commit();
		
		return events;
	
	}

	 
	public List<Event> getEvents() {
		
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.beginTransaction();
		
		List<Event> res = new ArrayList<Event>();
		Query query = s.createQuery("FROM Event");
		List<Event> events = query.list();
		s.getTransaction().commit();
		for (Event ev : events) {
			System.out.println(ev.toString());
			res.add(ev);
		}
		return res;
	}
	
	/**
	 * This method retrieves from the database the dates a month for which there are
	 * events
	 * 
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	public List<Date> getEventsMonth(Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.beginTransaction();
		
		List<Date> res = new ArrayList<Date>();

		Date firstDayMonthDate = UtilDate.firstDayMonth(date);
		Date lastDayMonthDate = UtilDate.lastDayMonth(date);

		Query query = s.createQuery("SELECT DISTINCT eventDate FROM Event WHERE eventDate BETWEEN :d1 and :d2");
		query.setParameter("d1", firstDayMonthDate);
		query.setParameter("d2", lastDayMonthDate);
		List<Date> dates = query.list();
		s.getTransaction().commit();

		for (Date d : dates) {
			System.out.println(d.toString());
			res.add(d);
		}
		return res;
	}


	public boolean existQuestion(Event event, String question) {
		System.out.println(">> DataAccess: existQuestion=> event= " + event + " question= " + question);
		
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.beginTransaction();
		
		Query q = s.createQuery("from Event where eventNumber=:eventNum");
		q.setParameter("eventNum", event.getEventNumber());
		Event ev = (Event) q.list().get(0);
		s.getTransaction().commit();

		return ev.DoesQuestionExists(question);

	}
	public List<Question> getEventQuestions(Event evento){
		
		System.out.println("Se va a empezar a recuperar las preguntas");
		Session s= HibernateUtil.getSessionFactory().getCurrentSession();
		s.beginTransaction();
		
		Query q = s.createQuery("From Question where event_eventNumber= :n");
		q.setParameter("n", evento.getEventNumber());
		List<Question> questionList= q.list();
		s.getTransaction().commit();
		return questionList;
	}


  }
