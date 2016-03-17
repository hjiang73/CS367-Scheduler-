///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  Scheduler.java
// File:             Event.java
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

/**
 * The event class represent a node in the resource tree.
 *
 * @author Han Jiang, You Wu
 */
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Event represents events to be held in .
 */
public class Event implements Interval{
	//data field
	private long start;
	private long end;
	private String name;
	private String resource;
	private String organization;
	private String description;


	Event(long start, long end, String name, String resource, String organization, String description){
		if(name==null||resource==null||organization==null||description==null){
			throw new IllegalArgumentException();
		}
		if(start<0||end<0||start>end){
			throw new IllegalArgumentException();
		}
		//constructor
		this.start = start;
		this.end = end;
		this.name = name;
		this.resource = resource;
		this.organization = organization;
		this.description = description;
	}

	/**
	 * Returns the start of an interval.
	 *
	 * @return start
	 */
	@Override
	public long getStart(){
		return start;
	}

	/**
	 * Returns the end of an interval.
	 *
	 * @return end
	 */
	@Override
	public long getEnd(){
		return end;
	}

	/**
	 * Returns the name of the event.
	 *
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the resource for the event.
	 *
	 * @return resource
	 */
	public String getResource() {
		return resource;	
	}

	/**
	 * Returns the organization for the event.
	 *
	 * @return organization
	 */
	public String getOrganization(){
		return organization;
	}

	/**
	 * Returns the description for the event.
	 *
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Compares two events of type Interval. Returns -1 if the start timestamp 
	 * of this interval-type event is less than the start timestamp of the other
	 * interval-type event, otherwise returns 1.
	 *
	 * @param Interval i
	 * @return -1 or 1
	 */
	@Override
	public int compareTo(Interval i) {
		if(this.start<i.getStart()){
			return -1;
		}
		else
			return 1;
	}

	/**
	 * Return true if the start timestamp of this event is equal to the start 
	 * timestamp of the other event, else returns false.
	 *
	 * @param Event e
	 * @return true or false
	 */
	public boolean equals(Event e) {
		return this.start == e.getStart();
	}

	/**
	 * Returns true if the otherInterval object overlaps with this interval,
	 * otherwise returns false. Two intervals overalap, if their timing intersection
	 * is non-zero.
	 *
	 * @param Interval i
	 * @return true or false
	 */
	@Override
	public boolean overlap(Interval i) {
		if (this.end < i.getStart() || this.start > i.getEnd()) 
			return false;
		return true;

	}

	/**
	 * Returns the string representation of this event.
	 *
	 * @return string representation
	 */
	@Override
	public String toString(){
		Date date1=new Date(start*1000);
		SimpleDateFormat df1 = new SimpleDateFormat("MM/dd/yyyy,HH:mm");
		String startdate = df1.format(date1);
		Date date2=new Date(end*1000);
		SimpleDateFormat df2 = new SimpleDateFormat("MM/dd/yyyy,HH:mm");
		String enddate = df2.format(date2);
		String s1 = "";
		s1 += name+"\n";
		s1 += "By: "+organization+"\n";
		s1 += "In: "+resource+"\n";
		s1 += "Start: "+startdate+"\n";
		s1 += "End: "+enddate+"\n";
		s1 += "Description: "+description+"\n";

		return s1;
	}
}
