///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            P4
// Files:            "Event.java"
//                   "Resource.java"
//                   "Interval.java"
//                   "IntervalBST.java"
//                   "IntervalBSTIterator.java"
//                   "IntervalBSTnode.java"
//                   "IntervalConflictException.java"
//                   "Scheduler.java"
//                   "SchedulerDB.java"
// Semester:         CS 367
//
// Author:           Han Jiang
// Email:            (your email address)
// CS Login:         hjiang
// Lecturer's Name:  James Skretney
// Lab Section:      Lec-002
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
//                   CHECK ASSIGNMENT PAGE TO see IF PAIR-PROGRAMMING IS ALLOWED
//                   If pair programming is allowed:
//                   1. Read PAIR-PROGRAMMING policy (in cs302 policy) 
//                   2. choose a partner wisely
//                   3. REGISTER THE TEAM BEFORE YOU WORK TOGETHER 
//                      a. one partner creates the team
//                      b. the other partner must join the team
//                   4. complete this section for each program file.
//
// Pair Partner:     You Wu
// Email:            wu278@wisc.edu
// CS Login:         ywu
// Lecturer's Name:  James Skretney
// Lab Section:      Lec-001
//
///////////////////////////////////////////////////////////////////////////////
import java.io.File;
import java.io.FileNotFoundException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * The application program, Scheduler, creates and uses a SchedulerDB object 
 * to represent and process information. 
 * The resources and events information 
 * is read from the initialization text file 
 * and then the program continues to process a set of user commands 
 * until terminated. 
 * 
 * <p>Bugs: None known
 * @author Han Jiang, You Wu
 */

public class Scheduler {
	//data field
	private static SchedulerDB schedulerDB = new SchedulerDB();
	private static Scanner scanner = null;

	/**
	 * Main method 
	 */
	public static void main(String args[]) {
		if (args.length != 1) {			
			System.err.println("Usage: java Scheduler <resourceListFile>");
			System.exit(1);
		}

		boolean didInitialize = 
				initializeFromInputFile(args[0]);

		if(!didInitialize) {
			System.err.println("Failed to initialize the application!");
			System.exit(1);
		}

		System.out.println("Welcome to the UW-Madison Scheduling Client");

		processUserCommands();
	}
	/**
	 * read from the initialization text file and create the database
	 * 
	 * @return boolean 
	 */
	private static boolean initializeFromInputFile(String resourceListFile) {
		File inFile = new File(resourceListFile);

		Scanner sc;
		try {
			sc = new Scanner(inFile);
			String resourcename = null;
			while(sc.hasNextLine()){
				String line = sc.nextLine();
				if(line.charAt(0) == '#'){
					resourcename = sc.nextLine();
					//add the resource to the database
					schedulerDB.addResource(resourcename);		
				}
				else{
					String name = line;
					String starttime = sc.nextLine();
					String endtime = sc.nextLine();
					long start = convertToTime(starttime);
					long end = convertToTime(endtime);
					String org = sc.nextLine();
					String description = sc.nextLine();
					//create and add the event to database
					schedulerDB.addEvent(resourcename, start, end, name, org, description);
				}
			}
			sc.close();
			return true;
			//close scanner and return true

		} catch (FileNotFoundException e) {
			return false;

		} 


	}

	private static void processUserCommands() {
		scanner = new Scanner(System.in);
		String command = null;		
		do {

			System.out.print("\nPlease Enter a command ([H]elp):");
			command = scanner.next();
			switch(command.toLowerCase()) {							
			case "v":
				processDisplayCommand();
				break;
			case "a":
				processAddCommand();
				break;
			case "d":
				processDeleteCommand();
				break;
			case "q":
				System.out.println("Quit");
				break;
			case "h":
				System.out.println("\nThis scheduler has commands that are entered as a single character indicated in [].\n"+
						"The main commands are to view, add, delete, or quit.\n"+
						"The first three main commands need a secondary command possibly with additional input.\n"+
						"A secondary command's additional input is described within <>.\n"+
						"Please note that a comma (,) in the add event command represents a need to press\n"+
						"the return character during the command. Also note that times must be in the format\n"+
						"of mm/dd/yyyy,hh:mm.\n"+
						"[v]iew\n"+
						"	[r] = view all resources\n"+
						"	[e] = view all events\n"+
						"	[t] <resource name> = view events in a resource\n"+
						"	[o] <organization name> = view events with an organization\n"+
						"	[n] <start time> <end time> = view events within a time range\n"+
						"	[s] <start time> <end time> <resource name> = view events within in a time range in a resource\n"+
						"[a]dd\n"+
						"	[r] <resource name> = add a resource\n"+
						"	[e] <resource name>, = add an event\n"+
						"		      <start time> <end time> <event name>, \n"+
						"		      <organization name>, \n"+
						"		      <event description>\n"+
						"[d]elete\n"+
						"	[r] <resource name> = delete a resource\n"+
						"	[e] <event start time> <resource name> = delete an event\n"+
						"[q]uit\n");
				break;
			default:
				System.out.println("Unrecognized Command!");
				break;
			}
		} while (!command.equalsIgnoreCase("q"));
		scanner.close();
	}


	private static void processDisplayCommand() {
		String restOfLine = scanner.next();
		Scanner in = new Scanner(restOfLine);
		String subCommand = in.next();
		switch(subCommand.toLowerCase()) {
		//additional input in comments (comma means return)
		case "r": 
			printResourceList(schedulerDB.getResources());
			break;
		case "e": 
			printEventList(schedulerDB.getAllEvents());
			break;
		case "t": // resource,
			printEventList(schedulerDB.getEventsInResource(scanner.nextLine().trim()));
			break;
		case "s": // start end resource,
			printEventList(schedulerDB.getEventsInRangeInResource(
					convertToTime(scanner.next()), convertToTime(scanner.next()), 
					scanner.nextLine().trim()));
			break;
		case "o": // organization
			printEventList(schedulerDB.getEventsForOrg(scanner.nextLine().trim()));
			break;
		case "n": // start end
			printEventList(schedulerDB.getEventsInRange(convertToTime(scanner.next()),
					convertToTime(scanner.next())));
			break;
		default: 
			System.out.println("Unrecognized Command!");
		}
		in.close();
	}

	private static void processAddCommand(){
		String restOfLine = scanner.next();
		Scanner in = new Scanner(restOfLine);
		String subCommand = in.next();
		switch(subCommand.toLowerCase()) {
		case "r": //resource
			if(!schedulerDB.addResource(scanner.nextLine().trim())){
				System.out.println("Could not add: no two resources may have the same name.");
			}else{
				System.out.println("Successfully added resource.");
			}
			break;
		case "e": //resource, start end name, organization, description
			try{
				if(!schedulerDB.addEvent(scanner.nextLine().trim(), 
						convertToTime(scanner.next()), convertToTime(scanner.next()), 
						scanner.nextLine().trim(), scanner.nextLine().trim(), scanner.nextLine().trim())){
					System.out.println("Could not add: resource not found.");
				}else{
					System.out.println("Successfully added event.");
				}
			}catch(IntervalConflictException expt){
				System.out.println("Could not add: this event conflicted with an existing event.");
			}
			break;
		default: 
			System.out.println("Unrecognized Command!");
		}
		in.close();
	}



	private static void processDeleteCommand(){
		String restOfLine = scanner.next();
		Scanner in = new Scanner(restOfLine);
		String subCommand = in.next();
		switch(subCommand.toLowerCase()) {
		case "r": // resource
			if(!schedulerDB.removeResource(scanner.nextLine().trim())){
				System.out.println("Could not delete. Resource not found.");
			}else{
				System.out.println("Successfully deleted resource.");
			}
			break;
		case "e":  // resource, start
			if(!schedulerDB.deleteEvent(convertToTime(scanner.next().trim()), 
					scanner.nextLine().trim())){
				System.out.println("Could not delete. Resource not found.");
			}else{
				System.out.println("Successfully deleted event.");
			}
			break;
		default: 
			System.out.println("Unrecognized Command!");
		}
		in.close();
	}

	private static void printResourceList(List<Resource> list){
		Iterator<Resource> itr = list.iterator();
		if(!itr.hasNext()){
			System.out.println("No resources to display.");
		}
		while(itr.hasNext()){
			System.out.println(itr.next().getName());
		}
	}

	private static void printEventList(List<Event> list){
		Iterator<Event> itr = list.iterator();
		if(!itr.hasNext()){
			System.out.println("No events to display.");
		}
		while(itr.hasNext()){
			System.out.println("\n" + itr.next().toString());
		}
	}

	private static long convertToTime(String time){
		long result = 0;
		Format format = new SimpleDateFormat("MM/dd/yyyy,HH:mm");
		try{
			Date date = (Date) format.parseObject(time);
			result = date.getTime()/1000;
		}catch(Exception e){
			System.out.println("Dates are not formatted correctly.  Must be \"MM/dd/yyyy,HH:mm\"");
		}
		return result;
	}

}



