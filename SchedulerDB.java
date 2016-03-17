///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  Scheduler.java
// File:             SchedulerDB.java
// Semester:         CS367 Fall 2015
//
// Author:           Han Jiang
// CS Login:         hjiang
// Lecturer's Name:  James Skretney
// Lab Section:      Lec-002
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
// Pair Partner:     You Wu
// Email:            wu278@wisc.edu
// CS Login:         ywu
// Lecturer's Name:  James Skretney
// Lab Section:      Lec-001
//
////////////////////////////////////////////////////////////////////////////////
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The SchedulerDB class represents a single database on the Scheduler. It stores
 * It stores various resources.
 * <p>Bugs: None known
 * @author Han Jiang, You Wu
 */

public class SchedulerDB {
	//data field
	private List<Resource> resourcedb;


	SchedulerDB(){
		//Constructor
		resourcedb = new ArrayList<Resource> ();
	}

	/**
	 * Add one resource to the database,and return 
	 * if added correctly.
	 *
	 * @return boolean 
	 */
	public boolean addResource(String resource){
		if(resource == null) return false;
		//resource is null, return false
		//otherwise add a new resource 
		//and return false
		Iterator<Resource> itr = resourcedb.iterator();
		while(itr.hasNext()){
			if(itr.next().getName().equals(resource)){
				return false;
			}
		}
		resourcedb.add(new Resource(resource));
		return true;

	}

	/**
	 * Remove one resource from the database,and return 
	 * if removed correctly.
	 *
	 * @return boolean 
	 */
	public boolean removeResource(String r){
		boolean found = false;
		if(r == null) return false;
		//resource is null, return false
		Iterator<Resource> itr = resourcedb.iterator();
		while(itr.hasNext()){
			if(itr.next().getName().equals(r)){
				itr.remove();
				found = true;
			}
		}
		//To find the resource in the database
		return found;
	}

	/**
	 * Add one event to certain resource the database,
	 * and return if added correctly.
	 *
	 * @return boolean 
	 */
	public boolean addEvent(String r, long start, long end, String name, 
			String organization, String description){
        if(start == end)
        	return false;
		try{
			Event e1 = new Event(start,end,name,r,organization,description);
			
		}
		catch(IllegalArgumentException ex){
			return false;
		}
		//find the resource in database by using iterator
		Iterator<Resource> itr = resourcedb.iterator();
		boolean added = false;
		while(itr.hasNext()){
			Resource tmp = itr.next() ;
			if(tmp.getName().equals(r)){
				Iterator<Event> itr2 = tmp.iterator();
				while(itr2.hasNext()){
					if(itr2.next().overlap(new Event(start,end,name,r,organization,description))){
						throw new IntervalConflictException();
					}
				}

				added =  tmp.addEvent(
						new Event(start,end,name,r,organization,description));
				//if found, add the event to that resource.
			}
		}
		return added;
	}

	/**
	 * Delete one event from the certain resource in 
	 * the database,and return if deleted correctly.
	 *
	 * @return boolean 
	 */
	public boolean deleteEvent(long start, String resource){
		if(resource == null) return false;
		boolean deleted = false;
		Resource tmp = findResource(resource);
		if(tmp==null){
			return false;
		}
		//find the resource in database by using iterator
		Iterator<Event> itr1 = tmp.iterator();
		while(itr1.hasNext()){
			Event tmp2 = itr1.next();
			if(tmp2.getStart()==start){			
				deleted=tmp.deleteEvent(start);
				//if found, delete the event 
			}
		}
		return deleted;

	}

	/**
	 * Returns a resource given its name.
	 *
	 * @return Resource 
	 */
	public Resource findResource(String r){
		
		Resource tmp1 = null;
		//find the resource in database by using iterator
		Iterator<Resource> itr = resourcedb.iterator();
		while(itr.hasNext()){
			Resource tmp = itr.next() ;
			if(tmp.getName().equals(r)){
				tmp1 = tmp;
			}
		}
		return tmp1;
	}

	/**
	 * Returns a list of resources maintained by this SchedulerDB class.
	 *
	 * @return List of Resource 
	 */
	public List<Resource> getResources(){
		return resourcedb;
	}

	/**
	 * Returns a list of events for the given resource.
	 *
	 * @return List of Events
	 */
	public List<Event> getEventsInResource(String resource){
		if(resource == null) return null;
		List<Event> eventsInRs = new ArrayList<Event>();
		//find the resource in database by using iterator
		Iterator<Resource> itr = resourcedb.iterator();
		while(itr.hasNext()){
			Resource tmp = itr.next() ;
			if(tmp.getName().equals(resource)){
				//if found, add all events in the resource
				//to the list just created
				Iterator<Event> itr2 = tmp.iterator();
				while(itr2.hasNext()){
					eventsInRs.add(itr2.next());
				}

			}
		}
		return eventsInRs;
	}

	/**
	 * Returns a list of events from all resources 
	 * between the given start and end.
	 * @return List of Events
	 */
	public List<Event> getEventsInRange(long start, long end){
		List<Event> eventsInrange = new ArrayList<Event>();
		Iterator<Resource> itr = resourcedb.iterator();
		while(itr.hasNext()){
			Resource tmp = itr.next() ;
			//using iterator to find every event in the database
			Iterator<Event> bstitr  = tmp.iterator();
			while(bstitr.hasNext()){
				Event tmp2 = bstitr.next();
				//if the event in the range, 
				//add the events to the list just created
				if(tmp2.getEnd()>=start&&tmp2.getStart()<=end){
					eventsInrange.add(tmp2);
				}
			}
		}
		return eventsInrange;

	}	

	/**
	 * Returns a list of events from 
	 * the given resource between the given start and end.
	 * @return List of Events
	 */


	public List<Event> 
	getEventsInRangeInResource(long start, long end, String resource){
		if(resource == null) return null;
		List<Event> eventsInRg = new ArrayList<Event>();
		Iterator<Resource> itr = resourcedb.iterator();
		while(itr.hasNext()){
			Resource tmp = itr.next() ;
			//find the resource in database by using iterator
			if(tmp.getName().equals(resource)){
				Iterator<Event> itr3  = tmp.iterator();
				//using iterator to find every event in the resource
				while(itr3.hasNext()){
					Event tmp2 = itr3.next();
					//if the event in the range, 
					//add the events to the list just created
					if(tmp2.getEnd()>=start&&tmp2.getStart()<=end){
						eventsInRg.add(tmp2);
					}
				}
			}

		}

		return eventsInRg;

	}

	/**
	 * Returns the list of all the events 
	 * stored in the SchedulerDB for the all the resources.
	 * @return List of Events
	 */
	public List<Event> getAllEvents(){
		List<Event> allevents = new ArrayList<Event>();
		Iterator<Resource> itr = resourcedb.iterator();
		//using iterator to find every event in the database
		while(itr.hasNext()){
			Resource tmp = itr.next() ;
			Iterator<Event> bstitr  = tmp.iterator();
			while(bstitr.hasNext()){
				allevents.add(bstitr.next());
				//add every event to the list just created
			}
		}
		return allevents;
	}

	/**
	 * Returns a list of events for a given organization.
	 *
	 * @return List of Events
	 */
	public List<Event> getEventsForOrg(String org){
		if(org == null) return null;
		List<Event> eventsForOrg = new ArrayList<Event>();
		Iterator<Resource> itr = resourcedb.iterator();
		while(itr.hasNext()){
			Resource tmp = itr.next() ;
			Iterator<Event> itr4  = tmp.iterator();
			while(itr4.hasNext()){
				Event tmp2 = itr4.next();
				//find the organization in database by using iterator
				if(tmp2.getOrganization().equals(org)){
					eventsForOrg.add(tmp2);
					//add all events of the org to the list created
				}

			}
		}
		return eventsForOrg;		
	}
}