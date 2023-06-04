import {Component, Input, OnChanges, OnInit} from '@angular/core';
import {VideoInstructionService} from "./video-instruction.service";
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";

@Component({
  selector: 'app-video-instruction',
  templateUrl: './video-instruction.component.html',
  styleUrls: ['./video-instruction.component.css']
})
export class VideoInstructionComponent implements OnInit, OnChanges {
  source: SafeUrl;
  @Input() videoUrl: string;
  loading: boolean;


  constructor(
    private videoInstructionService: VideoInstructionService,
    private sanitizer: DomSanitizer)
  { }

  ngOnInit(): void {
    this.loading = true;
    this.downloadVideoInstruction(this.videoUrl);
  }
  ngOnChanges(): void {
    this.loading = true;
    this.downloadVideoInstruction(this.videoUrl);
  }

  downloadVideoInstruction(url: string) {
    this.videoInstructionService
      .downloadVideoInstruction(this.extractVideoInstructionName(url))
      .subscribe((response) => {

        var blobUrl = URL.createObjectURL(response!);
        this.source = this.sanitizer.bypassSecurityTrustUrl(blobUrl);
        this.loading = false;
      });
  }

  private extractVideoInstructionName(url: string) {
    if(url.includes('/')){
      return url.substring(url.lastIndexOf('/') + 1);
    }
    return url.substring(url.lastIndexOf('\\') + 1);
  }


}
