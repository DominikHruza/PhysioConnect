export interface WorkoutPlan {
  id: number;
  practitionerId: number;
  patientId: number;
  startAt: string;
  endAt: string;
  description: string;
  effortLevel: number;
  painLevel: number;
  isActive: boolean;
  exercises: PlannedExercise[]
}

export interface PlannedExercise {
  id: number;
  exrxName: string;
  exrxSets: number;
  exrxRepetition: number;
  videoInstrUrl: string;
  description: string;
  effortLevel: number;
  painLevel: number;
  patientComment: string;
  isCompleted: boolean;
}
