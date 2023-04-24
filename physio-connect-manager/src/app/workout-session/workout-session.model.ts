export interface WorkoutSession {
  planId: number
  startedAt: string
  completedAt: string
  patientComment: string
  completedExercises: CompletedExercise[]
}

export interface CompletedExercise {
  exerciseId: number,
  name: string
  completedSets: number
  completedRepetition: number
  effortLevel: number
  painLevel: number
  patientComment: string
}
