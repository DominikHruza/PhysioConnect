export interface WorkoutPlan {
  practitionerId: number,
  patientId: number,
  exercises: Exercise[]
}
export interface WorkoutPlanDto {
  id:number
  practitionerId: number,
  patientId: number,
  exercises: ExerciseDto[],
  startAt: string;
  endAt: string;
  description: string;
  effortLevel: number;
  painLevel: number;
  isActive: boolean;
}

export interface Exercise {
  id?: number ,
  name: string,
  sets: number,
  reps: number,
  description: string,
  file: File | null
}

export interface ExerciseDto {
  id?: number | null,
  exrxName: string,
  exrxSets: number,
  exrxRepetition: number,
  description: string,
  videoInstrUrl: File | string | null
}


