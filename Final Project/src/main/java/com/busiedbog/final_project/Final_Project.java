package com.busiedbog.final_project;

import java.util.Date;

/**
 *
 * @author corti
 */
public class Final_Project {

    interface Task {
        
        float completion = 0;
        String name = null;
        String description = null;
        int reward = 0;
        
        float getCompletion();
        void setCompletion(float newCompletion);
        String getName();
        void setName(String newName);
        String getDescription();
        void setDescription(String newDesc);
        int getReward();
        void setReward(int newReward);
        void markComplete();
        
        }
    
    public static class SingleUseTask implements Task {
        
        private float completion;
        private String name;
        private String description;
        private int reward;
        
        private Date dueDate;
        private Date completionDate;
        
        SingleUseTask() {
            completion = 0;
            name = "New Task";
            description = "Complete a task";
            reward = 0;
        }
        SingleUseTask(int taskCompletion, String taskName, String taskDesc, int taskReward) {
            completion = taskCompletion;
            name = taskName;
            description = taskDesc;
            reward = taskReward;
        }
        
        @Override
        public float getCompletion() {
            return completion;
        }
        @Override
        public void setCompletion(float newCompletion) {
            completion = newCompletion;
        }
        @Override
        public String getName() {
            return name;
        }
        @Override
        public void setName(String newName) {
            name = newName;
        }
        @Override
        public String getDescription() {
            return description;
        }
        @Override
        public void setDescription(String newDesc) {
            description = newDesc;
        }
        @Override
        public int getReward() {
            return reward;
        }
        @Override
        public void setReward(int newReward) {
            reward = newReward;
        }
        @Override
        public void markComplete() {
            completion = 1;
        }
        public Date getDueDate() {
            return dueDate;
        }
        public void setDueDate(Date newDueDate) {
            dueDate = newDueDate;
        }
        public Date getCompletionDate() {
            return completionDate;
        }
        public void setCompletionDate(Date newCompletionDate) {
            completionDate = newCompletionDate;
        }
    }
        
    public static class RepeatingTask implements Task {
        
        private float completion;
        private String name;
        private String description;
        private int reward;
        
        private Date lastCompletedDate;
        private int repeatTime;
        private Date dueDate;
        private int timesCompleted;
        
        RepeatingTask() {
            completion = 0;
            name = "New Task";
            description = "Complete a task";
            reward = 0;
            lastCompletedDate = null;
            repeatTime = 0;
            dueDate = null;
            timesCompleted = 0;
        }
        RepeatingTask(int taskCompletion, String taskName, String taskDesc, 
                int taskReward, Date lastCompleted, int repeat, Date due, 
                int timesCompletedTask) {
            completion = taskCompletion;
            name = taskName;
            description = taskDesc;
            reward = taskReward;
            lastCompletedDate = lastCompleted;
            repeatTime = repeat;
            dueDate = due;
            timesCompleted = 0;
        }
        
        @Override
        public float getCompletion() {
            return completion;
        }
        @Override
        public void setCompletion(float newCompletion) {
            completion = newCompletion;
        }
        @Override
        public String getName() {
            return name;
        }
        @Override
        public void setName(String newName) {
            name = newName;
        }
        @Override
        public String getDescription() {
            return description;
        }
        @Override
        public void setDescription(String newDesc) {
            description = newDesc;
        }
        @Override
        public int getReward() {
            return reward;
        }
        @Override
        public void setReward(int newReward) {
            reward = newReward;
        }
        @Override
        public void markComplete() {
            completion = 1;
            timesCompleted += 1;
            lastCompletedDate = new Date();
        }
        public void resetTask() {
            Date currentDate = new Date();
            dueDate = new Date(currentDate.getTime() + repeatTime);
        }
        public int getTimesCompleted() {
            return timesCompleted;
        }
    }
    
        public static class Habit {

            private String name;
            private String description;
            private RepeatingTask[] relatedTasks;
            private int progress;
            private int completionTimes;

            Habit() {
                name = "New Habit";
                description = "Work on this habit";
                relatedTasks = null;
                progress = 0;
                completionTimes = 0;
            }
            Habit(String habitName, String habitDesc, RepeatingTask[] habitRelatedTasks,
                    int habitProgress, int timesCompleted) {
                name = habitName;
                description = habitDesc;
                relatedTasks = habitRelatedTasks;
                progress = habitProgress;
                completionTimes = timesCompleted;
            }

            String getName() {
                return name;
            }
            void setName(String newName) {
                name = newName;
            }
            String getDescription() {
                return description;
            }
            void setDescription(String newDesc) {
                description = newDesc;
            }
            Task[] getRelatedTasks() {
                return relatedTasks;
            }
            void setRelatedTask(RepeatingTask[] newRelatedTask) {
                relatedTasks = newRelatedTask;
            } 
            void updateProgress() {
                completionTimes = 0;
                if (relatedTasks != null) {
                    for (RepeatingTask relatedTask : relatedTasks) {
                        completionTimes += relatedTask.getTimesCompleted();
                    }
                }
            }

        }
}
    

