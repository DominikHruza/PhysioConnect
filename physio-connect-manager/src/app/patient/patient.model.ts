export interface Patient {
  id: number | null,
  practitionerId: number
  firstname: string,
  lastname: string,
  email: string,
  diagnosis: string
}
