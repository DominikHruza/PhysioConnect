export interface Visit {
  id: number;
  patient: Patient;
  practitioner: Practitioner;
  scheduledFor: string;
}

export interface Patient {
  id: number | null,
  practitionerId: number
  firstname: string,
  lastname: string,
  email: string,
  diagnosis: string
}

export interface Practitioner {
  id: number,
  firstname: string,
  lastname: string,
  email: string
}
