import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TaskService } from '../services/taskService/task.service';
import { AuthService } from '../services/userService/auth.service';
import { ActivatedRoute } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { RouterService } from '../services/routerService/router.service';
import { HttpErrorResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-update-todo',
  templateUrl: './update-todo.component.html',
  styleUrl: './update-todo.component.css'
})
export class UpdateTodoComponent implements OnInit {
  @Input() taskName: string = "";

  editTaskForm!: FormGroup;
  todo: any;
  taskStatus: boolean = false;

  constructor(private fb: FormBuilder, private taskService: TaskService, private auth: AuthService, private rs: ActivatedRoute, private snackBar: MatSnackBar, private router: RouterService, private route: ActivatedRoute, private datePipe: DatePipe) {
    this.editTaskForm = this.fb.group({
      taskName: ['', Validators.required],
      taskDescription: [''],
      taskDate: ['', Validators.required],
      taskStartTime: ['', Validators.required],
      taskDueTime: ['', Validators.required],
      isrepetitive: [''],
      repeatType: [''],
      repeatEndDate: ['', Validators.required],
      ishighPriority: [''],
      iscompleted: [''],
      isarchived: [''],
      isapplyToRepetitive: [false]
    });
  }

  ngOnInit(): void {
    this.taskService.currentTaskName.subscribe(taskName => this.taskName = taskName);
    console.log(this.taskName);

    this.taskService.getAParticluarTask(this.auth.token, this.taskName).subscribe((data) => {
      this.todo = data[0];
      this.setTaskField();
    },
      (error) => {
        console.error("Error adding task:", error);
      }
    );

    // this.taskService.markAsCompleted$.subscribe(() => {
    //   this.editTaskForm.get('iscompleted')?.setValue(true);
    //   this.taskStatus = true;
    //   this.onSubmit();
    // });
  }

  public setTaskField() {
    this.editTaskForm.patchValue(this.todo);
  }

  onSubmit() {
    var taskDate = this.datePipe.transform(this.editTaskForm.get('taskDate')?.value, 'yyyy-MM-dd');
    var repeatEndDate = this.datePipe.transform(this.editTaskForm.get('repeatEndDate')?.value, 'yyyy-MM-dd');

    var taskStartTime = this.datePipe.transform(taskDate + ' ' + this.editTaskForm.get('taskStartTime')?.value, 'yyyy-MM-dd HH:mm:ss');
    var taskDueTime = this.datePipe.transform(repeatEndDate + ' ' + this.editTaskForm.get('taskDueTime')?.value, 'yyyy-MM-dd HH:mm:ss');

    var dateTimeStringForSatrtTime: any;
    var dateTimeStringForEndTime: any;

    dateTimeStringForSatrtTime = taskStartTime;
    var startTimePart = dateTimeStringForSatrtTime.split(' ')[1];
    dateTimeStringForEndTime = taskDueTime;
    var endTimePart = dateTimeStringForEndTime.split(' ')[1];

    taskStartTime = startTimePart;
    taskDueTime = endTimePart;
  if (taskDate != repeatEndDate) {
    this.editTaskForm.get('isrepetitive')?.setValue(true);
  }

    var addedTask: any = {
      ...this.editTaskForm.value,
      taskDate,
      repeatEndDate,
      taskStartTime,
      taskDueTime
    };

    this.taskService.updateTask(addedTask, this.auth.token).subscribe((data) => {
      this.snackBar.open('Task Updated Successfully!', 'Close', {
        duration: 5000,
        verticalPosition: 'bottom',
        horizontalPosition: 'center',
        panelClass: ['success-snackbar']
      });
      this.router.navigateToView();
    },
      (error: HttpErrorResponse) => {
        console.error('Error updating task:', error);
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
      });
      
  }
  public toggleCompletionStatus(event: any): void {
    const isChecked = event.target.checked;
    // Update completion status in the form
    this.editTaskForm.get('iscompleted')?.setValue(isChecked);
    // Call onSubmit to save the updated completion status
    this.onSubmit();
  }

}






