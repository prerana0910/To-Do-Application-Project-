import { Component, EventEmitter, OnInit, Output, inject } from '@angular/core';

import { TaskService } from '../services/taskService/task.service';
import { AuthService } from '../services/userService/auth.service';

import { MatSnackBar } from '@angular/material/snack-bar';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RouterService } from '../services/routerService/router.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-veiw-todo',
  templateUrl: './veiw-todo.component.html',
  styleUrl: './veiw-todo.component.css'
})
export class VeiwTodoComponent implements OnInit {

  todo: any[] = [];
  editTaskForm!:FormGroup;
  deleteForm!:FormGroup;
  wantToEdit : boolean | undefined;
  wantToDelete : boolean | undefined;
  taskName: string = "";
  archivedTasks: any[] = [];
  showArchivedTasks: boolean = false;
  showHiddenCompletedTasks: boolean = false;
  hiddenCompletedTasks: any[] = [];
  filteredTodo: any[]=[];
  

  constructor(private taskService: TaskService, private auth: AuthService, private snackBar: MatSnackBar,private formBuilder:FormBuilder,private router:RouterService) { }

  ngOnInit(): void {
    this.getAllTasks();
  }
//   filterTasks(): void {
//     this.todo = this.todo.filter(task => !task.iscompleted && !task.isarchived);
// }

  getAllTasks() {
    this.taskService.getUserTask(this.auth.token).subscribe((data) => {
      this.todo = data;
      this.filteredTodo = this.todo.filter(task => !task.isarchived);
      });
      (error: any) => {
        console.log(error);
      };
      
  }
  @Output() taskNameEmitter: EventEmitter<string> = new EventEmitter<string>();

  

  // Method to set the taskName variable when a task is clicked
  
  setTaskName(clickedTaskName: string) {
    // Emit the task name when a task is clicked
    this.taskService.changeTaskName(clickedTaskName);
  }
  editTask(clickedTaskName: string) {
    this.setTaskName(clickedTaskName);
    // Perform any additional actions needed when editing a task
    this.router.navigateToUpdate();
    
  }
  deleteTask(clickedTaskName: string){
    let confirmvalue = confirm('Are you sure , Do you want to delete ?');
      if (confirmvalue) {
    this.taskService.getAParticluarTask(this.auth.token, clickedTaskName).subscribe((data) => {
      this.taskService.deleteTask(data[0],this.auth.token).subscribe((data)=>{
        this.snackBar.open('Task deleted Successfully!', 'Close', {
          duration: 5000, 
          verticalPosition: 'bottom', 
          horizontalPosition: 'center',
          panelClass: ['success-snackbar']
       });
       this.getAllTasks();
      },
      (error: HttpErrorResponse) => {
        console.error('Error logging user:', error);
        let errorMessage = error.error;
        if (error.error && error.error.message) {
          errorMessage = error.error.message;
        }
        this.snackBar.open(errorMessage, 'Close', {
          duration: 5000,
          verticalPosition: 'bottom',
          horizontalPosition: 'center',
          panelClass: ['error-snackbar']
        });
      })
    },
      (error) => {
        console.error("Error deleting task:", error);
      }
    

  )}}
  markAsCompleted(taskName: string) {
    // Find the task with the specified task name
    const taskToUpdate = this.todo.find(task => task.taskName === taskName);
  
    if (!taskToUpdate) {
      console.error('Task not found:', taskName);
      return; // Return early if the task is not found
    }
  
    // Toggle the completion status
    taskToUpdate.iscompleted = !taskToUpdate.iscompleted;
    // if (taskToUpdate.iscompleted) {
    //   taskToUpdate.isarchived = true;
    // }
  
    // Update the task completion status using the task service
    this.taskService.updateTask(taskToUpdate,this.auth.token).subscribe(
      () => {
        const statusMessage = taskToUpdate.iscompleted ? 'Task marked as completed!' : 'Task marked as incomplete!';
        this.snackBar.open(statusMessage, 'Close', {
          duration: 5000,
          verticalPosition: 'bottom',
          horizontalPosition: 'center',
          panelClass: ['success-snackbar']
        });
        // Optionally, you can refresh the task list after updating
        this.getAllTasks();
      },
      (error) => {
        console.error('Error updating task:', error);
        this.snackBar.open('Error updating task. Please try again later.', 'Close', {
          duration: 5000,
          verticalPosition: 'bottom',
          horizontalPosition: 'center',
          panelClass: ['error-snackbar']
        });
        // Reset the completion status if update fails
        taskToUpdate.iscompleted = !taskToUpdate.iscompleted; // This line is removed to keep the original state
      // if (taskToUpdate.iscompleted) {
      //   taskToUpdate.isarchived = false;
      // } // This line is removed to keep the original state
      }
    );
  }
  toggleArchiveStatus(task: any) {
    if (!task.iscompleted) {
      task.isarchived = !task.isarchived; // Toggle the isarchived property only if task is not completed
      this.taskService.updateTask(task, this.auth.token).subscribe(
        () => {
          const statusMessage = task.isarchived ? 'Task archived!' : 'Task unarchived!';
          this.snackBar.open(statusMessage, 'Close', {
            duration: 5000,
            verticalPosition: 'bottom',
            horizontalPosition: 'center',
            panelClass: ['success-snackbar']
          });
          // Optionally, you can refresh the task list after updating
          this.getAllTasks();
        },
        (error) => {
          console.error('Error updating task:', error);
          this.snackBar.open('Error updating task. Please try again later.', 'Close', {
            duration: 5000,
            verticalPosition: 'bottom',
            horizontalPosition: 'center',
            panelClass: ['error-snackbar']
          });
          // Reset the isarchived status if update fails
          task.isarchived = !task.isarchived;
        }
      );
    } else {
      // Display a message indicating that task cannot be archived because it's completed
      this.snackBar.open('Cannot archive a completed task!', 'Close', {
        duration: 5000,
        verticalPosition: 'bottom',
        horizontalPosition: 'center',
        panelClass: ['error-snackbar']
      });
    }
  }
  toggleArchivedTasks() {
    this.showArchivedTasks = !this.showArchivedTasks;
    if (this.showArchivedTasks) {
        this.archivedTasks = this.todo.filter(task => task.isarchived);
    } else {
        this.archivedTasks = [];
    }
}

// toggleHiddenCompletedTasks() {
//     this.showHiddenCompletedTasks = !this.showHiddenCompletedTasks;
//     if (this.showHiddenCompletedTasks) {
//         // Filter out hidden completed tasks from archived tasks
//         this.hiddenCompletedTasks = this.archivedTasks.filter(task => task.iscompleted);
//     } else {
//         this.hiddenCompletedTasks = [];
//     }
// }

}
