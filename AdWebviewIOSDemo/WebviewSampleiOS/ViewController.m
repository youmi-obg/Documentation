//
//  ViewController.m
//  WebviewSampleiOS
//
//  Created by Martin on 2024/7/30.
//

#import "ViewController.h"
#import "H5WebviewController.h"
#import <WebKit/WebKit.h>


@interface ViewController ()


@end


@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    

}
- (IBAction)showWebview:(id)sender {
    UIViewController *rootVC = [UIApplication sharedApplication].keyWindow.rootViewController;
    H5WebviewController *viewController = [[H5WebviewController alloc] init];
    viewController.modalPresentationStyle = UIModalPresentationFullScreen;
    [rootVC presentViewController:viewController animated:YES completion:nil];
}

@end
