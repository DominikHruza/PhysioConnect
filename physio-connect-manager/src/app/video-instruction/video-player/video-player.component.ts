import { Component, Input, OnInit } from '@angular/core';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { VideoInstructionService } from '../video-instruction.service';

@Component({
  selector: 'app-video-player',
  templateUrl: './video-player.component.html',
  styleUrls: ['./video-player.component.css'],
})
export class VideoPlayerComponent implements OnInit {
  source: SafeUrl;
  @Input() videoUrl: string;

  constructor(
    private videoInstructionService: VideoInstructionService,
    private sanitizer: DomSanitizer
  ) {}

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
