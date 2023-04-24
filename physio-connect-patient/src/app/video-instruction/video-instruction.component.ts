import { Component, Input, OnInit } from '@angular/core';
import {VideoInstructionService} from "./video-instruction.service";
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";

@Component({
  selector: 'app-video-instruction',
  templateUrl: './video-instruction.component.html',
  styleUrls: ['./video-instruction.component.css']
})
export class VideoInstructionComponent implements OnInit {
  source: SafeUrl;
  @Input() videoUrl: string;


  constructor(
    private videoInstructionService: VideoInstructionService,
    private sanitizer: DomSanitizer)
  { }

  ngOnInit(): void {
    this.downloadVideoInstruction(this.videoUrl);
  }

  downloadVideoInstruction(url: string) {
    this.videoInstructionService
      .downloadVideoInstruction(this.extractVideoInstructionName(url))
      .subscribe((response) => {
        var blobUrl = URL.createObjectURL(response!);
        this.source = this.sanitizer.bypassSecurityTrustUrl(blobUrl);
      });
  }

  private extractVideoInstructionName(url: string) {
    if(url.includes('/')){
      return url.substring(url.lastIndexOf('/') + 1);
    }
    return url.substring(url.lastIndexOf('\\') + 1);
  }
}
