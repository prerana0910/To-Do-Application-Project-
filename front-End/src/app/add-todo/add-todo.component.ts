import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TaskService } from '../services/taskService/task.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { RouterService } from '../services/routerService/router.service';
import { DatePipe } from '@angular/common';
import { AuthService } from '../services/userService/auth.service';
import { ToDo } from '../models/Task';

@Component({
  selector: 'app-add-todo',
  templateUrl: './add-todo.component.html',
  styleUrl: './add-todo.component.css'
})
export class AddTodoComponent implements OnInit {
  constructor(private formBuilder : FormBuilder, private taskService : TaskService, 
    private authService : AuthService, private snackBar : MatSnackBar,
    private datePipe: DatePipe, private router : RouterService
    ) {
      this.minDate = this.datePipe.transform(new Date(), 'yyyy-MM-dd') || '';
  }
  minDate: string;
  taskForm!: FormGroup;
  ngOnInit(): void {
    this.setTaskField();
    this.setupRepeatTypeVisibility();
  }
  public setTaskField() {
    this.taskForm = this.formBuilder.group({
      taskName: ['', Validators.required],
      taskDescription: [''],
      taskDate: ['', Validators.required],
      taskStartTime: ['', Validators.required],
      taskDueTime: ['', Validators.required],
      isrepetitive: [],
      repeatType: [''],
      repeatEndDate: [''],
      ishighPriority: [],
      iscompleted: [false],
      isarchived: [false],
    });

  }
  setupRepeatTypeVisibility() {
    this.taskForm.get('taskDate')?.valueChanges.subscribe(() => {
      this.updateRepeatTypeVisibility();
    });

    this.taskForm.get('repeatEndDate')?.valueChanges.subscribe(() => {
      this.updateRepeatTypeVisibility();
    });

    this.updateRepeatTypeVisibility();
  }

  updateRepeatTypeVisibility() {
    const taskDate = this.taskForm.get('taskDate')?.value;
    const repeatEndDate = this.taskForm.get('repeatEndDate')?.value;

    if (taskDate && repeatEndDate) {
      const taskDateObj = new Date(taskDate);
      const repeatEndDateObj = new Date(repeatEndDate);

      if (repeatEndDateObj <= taskDateObj) {
        this.taskForm.get('repeatType')?.setValue('');
        this.taskForm.get('repeatType')?.disable();
      } else {
        this.taskForm.get('repeatType')?.enable();
      }
    }
  }
  onSubmit() {
    var taskDate = this.datePipe.transform(this.taskForm.get('taskDate')?.value, 'yyyy-MM-dd');
    var repeatEndDate = this.datePipe.transform(this.taskForm.get('repeatEndDate')?.value, 'yyyy-MM-dd');

    var taskStartTime = this.datePipe.transform(taskDate + ' ' + this.taskForm.get('taskStartTime')?.value, 'yyyy-MM-dd HH:mm:ss');
    var taskDueTime = this.datePipe.transform(repeatEndDate + ' ' + this.taskForm.get('taskDueTime')?.value, 'yyyy-MM-dd HH:mm:ss');

    var dateTimeStringForSatrtTime: any; 
    var dateTimeStringForEndTime: any; 

    dateTimeStringForSatrtTime = taskStartTime; 
    var startTimePart = dateTimeStringForSatrtTime.split(' ')[1];
    dateTimeStringForEndTime = taskDueTime;
    var endTimePart = dateTimeStringForEndTime.split(' ')[1];

    taskStartTime = startTimePart;
    taskDueTime = endTimePart;

    if(taskDate!=repeatEndDate){
      this.taskForm.get('isrepetitive')?.setValue(true);
    }

    var addedTask: ToDo = {
      ...this.taskForm.value,
      taskDate,
      repeatEndDate,
      taskStartTime,
      taskDueTime
    };
    console.log('71: ');
    console.log(addedTask);
    this.taskService.addTask(addedTask,this.authService.token).subscribe((data)=>{
      this.snackBar.open('Task Added Successfully!', 'Close', {
        duration: 5000, 
        verticalPosition: 'bottom', 
        horizontalPosition: 'center',
        panelClass: ['success-snackbar']
     });
     this.router.navigateToView();
     
    },
    (error) => {
      console.error("Error adding task:", error);
    }
    );
  }
}


