package com.todoapplication.todolist.services.todoservices;

import com.todoapplication.todolist.domain.ToDo;
import com.todoapplication.todolist.domain.User;
import com.todoapplication.todolist.exception.TaskAlreadyExistsException;
import com.todoapplication.todolist.exception.TodoNotFoundException;
import com.todoapplication.todolist.exception.UserNotFoundException;
import com.todoapplication.todolist.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service

public class ToDoServiceImpl implements IToDoService{
    private final ToDoRepository toDoRepository;
    @Autowired
    public ToDoServiceImpl(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    @Override
    public User getUser(String emailId) throws UserNotFoundException {
        Optional<User> fetchedUser = toDoRepository.findById(emailId);
        User user = fetchedUser.get();
        if(fetchedUser.isEmpty()) {
            throw new UserNotFoundException("User with the given EmailId didn't Exist");
        }
        else {
            return user;
        }
    }

    @Override
    public User saveToDo(ToDo toDo, String userEmail) throws UserNotFoundException, TaskAlreadyExistsException{
        Optional<User> fetchedUser = toDoRepository.findById(userEmail);
        User currentUser;
        if(fetchedUser.isEmpty()) {
            throw new UserNotFoundException("User with the given EmailId did't Exist");
        }
        else {
            currentUser = fetchedUser.get();
            List<ToDo> existingTasks = currentUser.getToDo();
            if(existingTasks == null) {
                existingTasks = new ArrayList<>();
                currentUser.setToDo(Arrays.asList(toDo));
            }
            else {
                boolean flag = false;
                for(ToDo iteratedTask : existingTasks) {
                    if(toDo.getTaskName()!= null && toDo.getTaskName().equalsIgnoreCase(iteratedTask.getTaskName())) {
                        flag = true;
                        break;
                    }
                }
                if(flag == true) {
                    throw new TaskAlreadyExistsException("Task with the given Name Already Exist, So If You want to add the Task Change the Task Name");
                }
            }

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

            toDo.setTaskDate(dateFormatter.format(LocalDate.parse(toDo.getTaskDate(), dateFormatter)));
            toDo.setTaskStartTime(timeFormatter.format(LocalTime.parse(toDo.getTaskStartTime(), timeFormatter)));
            toDo.setTaskDueTime(timeFormatter.format(LocalTime.parse(toDo.getTaskDueTime(), timeFormatter)));

            toDo.setIsapplyToRepetitive(false);

            if (toDo.getIsrepetitive()) {
                generateRepetitiveTasks(currentUser, toDo);
            }
            else {
                existingTasks.add(toDo);
                currentUser.setToDo(existingTasks);
            }

            toDoRepository.save(currentUser);
        }
        return currentUser;
    }
    private void generateRepetitiveTasks(User user, ToDo task) {
        LocalDate startDate = LocalDate.parse(task.getTaskDate());
        LocalDate endDate = LocalDate.parse(task.getRepeatEndDate());
        String repeatType = task.getRepeatType();

        List<ToDo> repetitiveTasks = new ArrayList<>();

        while (!startDate.isAfter(endDate)) {
            ToDo repetitiveTask = new ToDo(task.getTaskName(),task.getTaskDescription(),startDate.toString(),
                    task.getTaskStartTime(),task.getTaskDueTime(),task.getIsrepetitive(),task.getRepeatType(),
                    task.getRepeatEndDate(),task.getIshighPriority(),task.getIscompleted(),task.getIsarchived(),
                    task.getIsapplyToRepetitive());
            repetitiveTasks.add(repetitiveTask);

            switch (repeatType) {
                case "Daily":
                    startDate = startDate.plusDays(1);
                    break;
                case "Weekly":
                    startDate = startDate.plusWeeks(1);
                    break;
                case "Monthly":
                    startDate = startDate.plusMonths(1);
                    break;
            }
        }

        List<ToDo> existingTasks = user.getToDo();
        if (existingTasks == null) {
            user.setToDo(repetitiveTasks);
        } else {
            List<ToDo> updatedTasks = new ArrayList<>(existingTasks); // Create a new ArrayList
            for (ToDo repetitiveTask : repetitiveTasks) {
                boolean isDuplicate = updatedTasks.stream()
                        .anyMatch(existingTask -> existingTask.getTaskName().equals(repetitiveTask.getTaskName())
                                && existingTask.getTaskDate().equals(repetitiveTask.getTaskDate()));
                if (!isDuplicate) {
                    updatedTasks.add(repetitiveTask);
                }
            }
            user.setToDo(updatedTasks);
        }
    }

    @Override
    public List<ToDo> getAllTodo(String userEmail) throws UserNotFoundException,TodoNotFoundException {
        Optional<User> fetchedUser = toDoRepository.findById(userEmail);
        User currentUser;
        if(fetchedUser.isEmpty()) {
            throw new UserNotFoundException("User with the given EmailId did't Exist");
        }
        else {
            currentUser = fetchedUser.get();
            List<ToDo> existingTasks = currentUser.getToDo();
            if(existingTasks == null) {
                throw new TodoNotFoundException("No Task is Available for the User");
            }
            else {
                return existingTasks;
            }
        }
    }

    @Override
    public boolean deleteTodo(String userEmail, ToDo task) throws TodoNotFoundException, UserNotFoundException {
        Optional<User> fetchedUser = toDoRepository.findById(userEmail);
        User currentUser;
        if(fetchedUser.isEmpty()) {
            throw new UserNotFoundException("User with the given EmailId did't Exist");
        }
        else {
            currentUser = fetchedUser.get();
            List<ToDo> existingTasks = currentUser.getToDo();
            boolean flag = false;
            if(existingTasks == null) {
                throw new TodoNotFoundException("No Task is Available for this User");
            }
            else {
                Iterator<ToDo> iteratedTask = existingTasks.iterator();
                while(iteratedTask.hasNext()) {
                    ToDo currentTask = iteratedTask.next();
                    if(task.getTaskName() != null && currentTask.getTaskName().equalsIgnoreCase(task.getTaskName())){
                        iteratedTask.remove();
                        flag = true;
                        break;
                    }
                }
            }
            if(flag == true) {
                if (currentUser.getToDo().stream().anyMatch(t -> task.getTaskName().equalsIgnoreCase(task.getTaskName()) && task.getIsrepetitive() && task.getIsapplyToRepetitive())) {
                    deleteRepetitiveTasks(currentUser, task.getTaskName());
                }
                currentUser.setToDo(existingTasks);
                toDoRepository.save(currentUser);
                return flag;
            }
            else {
                throw new TodoNotFoundException("Task with the given TaskName didn't Exist");
            }
        }
    }
    private void deleteRepetitiveTasks(User user, String taskName) {
        List<ToDo> existingTasks = user.getToDo();
        existingTasks.removeIf(task -> task.getTaskName().equalsIgnoreCase(taskName) && task.getIsrepetitive() && task.getIsapplyToRepetitive());
        user.setToDo(existingTasks);
        toDoRepository.save(user);
    }

    @Override
    public User updateTodo(String userEmail, ToDo task) throws UserNotFoundException, TodoNotFoundException {
        Optional<User> fetchedUser = toDoRepository.findById(userEmail);
        User currentUser;
        DateTimeFormatter dateFormatter;
        DateTimeFormatter timeFormatter;
        if (fetchedUser.isEmpty()) {
            throw new UserNotFoundException("User with the given EmailId didn't Exist");
        } else {
            currentUser = fetchedUser.get();
            List<ToDo> existingTasks = currentUser.getToDo();
            if (existingTasks == null) {
                throw new TodoNotFoundException("No Task is Available for the User");
            } else {
                boolean flag = false;
                for (ToDo iteratedTask : existingTasks) {
                    if (task.getTaskName() != null && iteratedTask.getTaskName().equalsIgnoreCase(task.getTaskName())) {
                        iteratedTask.setTaskDescription(task.getTaskDescription());
                        iteratedTask.setTaskDate(task.getTaskDate());
                        iteratedTask.setTaskStartTime(task.getTaskStartTime());
                        iteratedTask.setTaskDueTime(task.getTaskDueTime());
                        iteratedTask.setIsrepetitive(task.getIsrepetitive());
                        iteratedTask.setRepeatType(task.getRepeatType());
                        iteratedTask.setRepeatEndDate(task.getRepeatEndDate());
                        iteratedTask.setIshighPriority(task.getIshighPriority());
                        iteratedTask.setIscompleted(task.getIscompleted());
                        iteratedTask.setIsarchived(task.getIscompleted()); // Archived should be set same as completed
                        iteratedTask.setIsapplyToRepetitive(task.getIsapplyToRepetitive());
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

                    if (task.getIsrepetitive() && task.getIsapplyToRepetitive()) {
                        updateRepetitiveTasks(currentUser, task, dateFormatter, timeFormatter);
                    }

                    if (task.getIsapplyToRepetitive() == false) {
                        for (ToDo iteratedTask : existingTasks) {
                            if (task.getTaskName() != null && iteratedTask.getTaskName().equalsIgnoreCase(task.getTaskName()) &&
                                    LocalDate.parse(iteratedTask.getTaskDate()).isEqual(LocalDate.parse(task.getTaskDate())) &&
                                    !iteratedTask.getIscompleted()) { // Check if the task for the specific date is not already completed
                                iteratedTask.setIscompleted(true);
                                iteratedTask.setIsarchived(true);
                                break; // No need to continue checking further tasks
                            }
                        }
                        toDoRepository.save(currentUser);
                    }


                }
                else {
                    throw new TodoNotFoundException("Task Name Doesn't Exist");
                }
            }
        }

        return currentUser;
    }
    private void updateRepetitiveTasks(User user, ToDo updatedTask, DateTimeFormatter dateFormatter, DateTimeFormatter timeFormatter) {
        List<ToDo> existingTasks = user.getToDo();
        boolean isUpdated = false;
        if (existingTasks != null) {
            List<ToDo> updatedTasks = new ArrayList<>();
            for (ToDo task : existingTasks) {
                if (task.getTaskName().equalsIgnoreCase(updatedTask.getTaskName()) && task.getIsrepetitive() && task.getIsapplyToRepetitive()) {
                    isUpdated = true;

                    task.setTaskDescription(updatedTask.getTaskDescription());
                    task.setTaskStartTime(updatedTask.getTaskStartTime());
                    task.setTaskDueTime(updatedTask.getTaskDueTime());
                    task.setRepeatType(updatedTask.getRepeatType());
                    task.setRepeatEndDate(updatedTask.getRepeatEndDate());
                    task.setIshighPriority(updatedTask.getIshighPriority());


                    LocalDate startDate = LocalDate.parse(task.getTaskDate());
                    LocalDate endDate = LocalDate.parse(task.getRepeatEndDate());
                    String repeatType = task.getRepeatType();


                    updatedTasks.removeIf(t -> t.getTaskName().equalsIgnoreCase(task.getTaskName()));

                    while (!startDate.isAfter(endDate)) {
                        ToDo repetitiveTask = new ToDo(task.getTaskName(), task.getTaskDescription(),
                                startDate.toString(), task.getTaskStartTime(), task.getTaskDueTime(),
                                task.getIsrepetitive(), task.getRepeatType(), task.getRepeatEndDate(),
                                task.getIshighPriority(), task.getIscompleted(), task.getIsarchived(),
                                task.getIsapplyToRepetitive());
                        updatedTasks.add(repetitiveTask);

                        switch (repeatType) {
                            case "Daily":
                                startDate = startDate.plusDays(1);
                                break;
                            case "Weekly":
                                startDate = startDate.plusWeeks(1);
                                break;
                            case "Monthly":
                                startDate = startDate.plusMonths(1);

                        }
                    }
                }
                else {
                    if(isUpdated == false) {
                        updatedTasks.add(task);
                    }
                }
            }
            // Update existingTasks in the user object

            user.setToDo(updatedTasks);
            toDoRepository.save(user);
        }
    }

    @Override
    public List<ToDo> getOneTask(String emailId, String taskName) throws UserNotFoundException, TodoNotFoundException {
        Optional<User> fetchedUser = toDoRepository.findById(emailId);
        User currentUser;
        if(fetchedUser.isEmpty()) {
            throw new UserNotFoundException("User with the given EmailId did't Exist");
        }
        else {
            currentUser = fetchedUser.get();
            List<ToDo> existingTasks = currentUser.getToDo();
            List<ToDo> neededTask = null;
            neededTask = new ArrayList<>();
            boolean flag = false;
            if(existingTasks == null) {
                throw new TodoNotFoundException("No Task is Available for the User");
            }
            for(ToDo iteratedTask : existingTasks) {
                if(taskName != null && iteratedTask.getTaskName().equalsIgnoreCase(taskName)) {
                    neededTask.add(iteratedTask);
                    flag = true;
                }
            }
            if(flag == true) {
                return neededTask;
            }
            else {
                throw new TodoNotFoundException("No Task is Available with the given Task Name");
            }
        }
    }
}
