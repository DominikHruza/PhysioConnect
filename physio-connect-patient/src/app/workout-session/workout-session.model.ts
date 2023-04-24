export interface WorkoutSession {
  planId: number;
  startedAt: string;
  completedAt: string;
  patientComment: string;
  completedExercises: SessionExercise[];
}

export interface SessionExercise {
  exerciseId: number;
  name: string;
  instructionUrl: string;
  completedSets: number;
  plannedSets: number;
  completedRepetition: number;
  plannedRepetition: number;
  effortLevel: number;
  painLevel: number;
  patientComment: string;
}
