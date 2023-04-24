import { Practitioner } from './Practitioner';
import { Patient } from '../patient/patient.model';

export interface Visit {
  id: number;
  patient: Patient;
  practitioner: Practitioner;
  scheduledFor: string;
}
