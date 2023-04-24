import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {WorkoutSession} from "./workout-session.model";

@Injectable({providedIn: "root"})
export class WorkoutSessionService {

  constructor(private httpClient: HttpClient) {}

  saveWorkoutSession(workoutSession: WorkoutSession){
    return this.httpClient
      .post('/api/exercise-session', workoutSession)
  }
}
