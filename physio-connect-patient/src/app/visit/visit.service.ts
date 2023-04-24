import {HttpClient} from "@angular/common/http";
import {Visit} from "./visit.model";
import {map} from "rxjs/operators";
import {Injectable} from "@angular/core";

@Injectable({ providedIn: 'root' })
export class VisitService {
  constructor(private httpClient: HttpClient) {}

  getVisits(patientId: number) {
    return this.httpClient
      .get<Visit[]>(`/api/visit/all/patient/${patientId}`)
      .pipe(
        map((visits) =>
          visits.sort(
            (visit1, visit2) =>
              new Date(visit1.scheduledFor).valueOf() -
              new Date(visit2.scheduledFor).valueOf()
          )
        )
      );
  }
}
