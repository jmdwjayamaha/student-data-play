/**
 * 
 */
package services;

import java.util.List;

import models.Student;
import akka.actor.UntypedActor;

import com.avaje.ebean.Ebean;
import common.PaginationData;

/**
 * @author VJAYAD1
 *
 */
public class ListStudentsActor extends UntypedActor {

    @Override
    public void onReceive(Object message) {

        PaginationData paginateData = (PaginationData) message;
        
        final List<Student> studentsList = Ebean.find(Student.class).findList();
        getSender().tell(studentsList, getSelf());
    }
}
